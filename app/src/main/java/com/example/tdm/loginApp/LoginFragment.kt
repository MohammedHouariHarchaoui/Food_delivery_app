package com.example.tdm.loginApp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.tdm.R
import com.example.tdm.data.SignInBody
import com.example.tdm.databinding.FragmentLoginBinding
import com.example.tdm.mainApp.MainActivity
import com.example.tdm.retrofit.Endpoint
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sessionManager.isLoggedIn) {
            navigateToMainActivity()
            return
        }




        binding.confirmSignInBtn.setOnClickListener {
            val signInBody =SignInBody(binding.passwordEditText.text.toString(),binding.emailEditText.text.toString())
            if (binding.passwordEditText.text.isNotEmpty() && binding.emailEditText.text.isNotEmpty()) {
                // Call the authenticate method from the Endpoint interface
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val response = Endpoint.createEndpoint().login(signInBody)
                        if (response.isSuccessful) {
                            // Authentication successful
                            val responseData = response.body()
                            // Do something with the authentication response data
                            Toast.makeText(requireContext(), "Authentication successful", Toast.LENGTH_SHORT).show()
                            sessionManager.isLoggedIn = true
                            sessionManager.userId=responseData!!.id
                            navigateToMainActivity()
                        } else {
                            // Authentication failed
                            Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Handle any exceptions that occur during the authentication process
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }


        binding.registerTextView.setOnClickListener(){
                view: View ->view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

//    fun authenticate(email : String,password : String) {
//        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//            this.runOnUiThread {
//                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
//            }
//
//        }
//        CoroutineScope(Dispatchers.IO+ exceptionHandler).launch {
//            val response = Endpoint.createEndpoint().authenticate(email,password)
//
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful && response.body() != null) {
//                    if(response.body()!!.id == 0){
//                        Toast.makeText(requireContext(), "Something went wrong1", Toast.LENGTH_SHORT).show()
//
//                    }else{
//                        binding.passwordEditText.setText("")
//                        binding.emailEditText.setText("")
//                        sessionManager.isLoggedIn = true
//                        navigateToMainActivity()
//
//                    }
//                } else {
//                        Toast.makeText(requireContext(), "Something went wrong2", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    private fun runOnUiThread(any: Any) {

    }

//    private fun createRequestBody(value: String): RequestBody {
//        return value.toRequestBody("text/plain".toMediaTypeOrNull())
//    }


}