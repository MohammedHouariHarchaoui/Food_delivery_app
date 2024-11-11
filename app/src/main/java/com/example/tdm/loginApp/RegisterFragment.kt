package com.example.tdm.loginApp

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.tdm.R
import com.example.tdm.databinding.FragmentRegisterBinding
import com.example.tdm.retrofit.Endpoint
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var sessionManager: SessionManager
    private val requestCode = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (sessionManager.isLoggedIn) {
            navigateToMainActivity()
            return
        }
        binding.setProfilePictureImageView.setOnClickListener(){
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGalleryIntent()
            } else {
                checkPermission()
            }
        }
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK && result.data != null) {
                    val imageUri = result.data?.data
                    if (imageUri != null) {
                        val imageBitmap =
                            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
                        val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 300, 300, true)
                        binding.setProfilePictureImageView.setImageBitmap(resizedBitmap)
                    }
                }
            }
        binding.confirmSignUpBtn.setOnClickListener {
            registerUser()

        }


    }


    private fun registerUser() {
//        val firstName = createRequestBody(binding.firstNameEditText.text.toString())
//        val lastName = createRequestBody(binding.lastNameEditText.text.toString())
//        val phoneNumber = createRequestBody(binding.editTextPhone.text.toString())
//        val address = createRequestBody(binding.addressEditText.text.toString()) // Replace with the actual address value
//        val password = createRequestBody(binding.passwordEditText.text.toString())
//        val email = createRequestBody(binding.emailEditText.text.toString())
//        val image = createProfileImagePart()
        val imageDrawable = binding.setProfilePictureImageView.drawable
        val bitmap = (imageDrawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        // Create a RequestBody for the image data
        val imageRequestBody = byteArrayOutputStream.toByteArray().toRequestBody("image/*".toMediaTypeOrNull())

// Create a MultipartBody.Part for the image
        val profileImagePart = MultipartBody.Part.createFormData("image", "profile_image.jpg", imageRequestBody)


        if (binding.firstNameEditText.text.isEmpty() || binding.lastNameEditText.text.isEmpty() || binding.editTextPhone.text.isEmpty() || binding.addressEditText.text.isEmpty() ||
            binding.passwordEditText.text.isEmpty() || binding.emailEditText.text.isEmpty() || binding.confirmPasswordEditText.text.isEmpty()
        ) {
            Toast.makeText(requireContext(), "Please fill in all the required fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.passwordEditText.text.toString() != binding.confirmPasswordEditText.text.toString()) {
            Toast.makeText(requireContext(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val response = Endpoint.createEndpoint().signUp(
                binding.firstNameEditText.text.toString().toRequestBody(),
                binding.lastNameEditText.text.toString().toRequestBody(),
                binding.editTextPhone.text.toString().toRequestBody(),
                binding.addressEditText.text.toString().toRequestBody(),
                binding.passwordEditText.text.toString().toRequestBody(),
                binding.emailEditText.text.toString().toRequestBody(),
                profileImagePart
            )

            if (response!!.isSuccessful) {
                val responseBody = response.body()
                // Handle the successful registration response
                // Access the success status and message using responseBody.success and responseBody.message respectively
                sessionManager.userId=responseBody!!.id
                sessionManager.isLoggedIn = true
                navigateToMainActivity()
            } else {
                // Handle the error response
                // Access the error message using response.message()
            }
        }
    }


    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun createProfileImagePart(): MultipartBody.Part {
        // Retrieve the profile image bitmap from ImageView and create a file
        val bitmap = (binding.setProfilePictureImageView.drawable as BitmapDrawable).bitmap
        val file = File(requireContext().cacheDir, "hooooooooooo.jpg")
        file.outputStream().use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        // Create the request body using the file and return it as MultipartBody.Part
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }


    private fun openGalleryIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncher.launch(intent)
    }

    private fun checkPermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == this.requestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGalleryIntent()
        }
    }

    private fun navigateToMainActivity() {
        view?.findNavController()?.navigate(R.id.action_registerFragment_to_mainActivity)
        val fm = requireActivity().supportFragmentManager
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


}