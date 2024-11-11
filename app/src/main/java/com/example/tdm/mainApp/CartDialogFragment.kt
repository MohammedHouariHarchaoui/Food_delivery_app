import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.tdm.adapters.CartAdapter
import com.example.tdm.data.Order
import com.example.tdm.data.OrderItem
import com.example.tdm.databinding.CartDialogLayoutBinding
import com.example.tdm.loginApp.SessionManager
import com.example.tdm.models.RestaurantModel
import com.example.tdm.retrofit.Endpoint
import com.example.tdm.room.AppDatabase
import kotlinx.coroutines.launch

class CartDialogFragment : DialogFragment() {
    lateinit var binding: CartDialogLayoutBinding
    lateinit var cartItemModel: CartItemModel
    lateinit var restaurantModel : RestaurantModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(requireContext())
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CartDialogLayoutBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idUser= sessionManager.userId
        val appDatabase = AppDatabase.buildDatabase(requireActivity())

        cartItemModel = ViewModelProvider(requireActivity())[CartItemModel::class.java]
        restaurantModel = ViewModelProvider(requireActivity())[RestaurantModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            cartItemModel.initialize(appDatabase?.cartItemDao())
            binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            binding.cartRecyclerView.adapter = CartAdapter(requireActivity(), cartItemModel.data, cartItemModel, this@CartDialogFragment)
            updateUi()
        }
        binding.emptyCartBtn.setOnClickListener {
            lifecycleScope.launch {
                cartItemModel.emptyCart()
                updateUi()
            }
        }

        binding.cartDialogConfirmBtn.setOnClickListener(){
            if(binding.cartItemCountTextView.text.toString().toInt()>0){
                val cartItems = cartItemModel.data
                val deliveryNote =binding.notesTextTextMultiLine2.text.toString()
                val address= binding.cartAddressEditText.text.toString()
                val orderItems = cartItems.map { cartItem ->
                    OrderItem(
                        idDish = cartItem.IdDish,
                        quantity = cartItem.quantity,
                        dishNote = cartItem.dishNote
                    )
                }
                val order=Order(orderItems,address,deliveryNote,idUser)
                val endpoint = Endpoint.createEndpoint()
                lifecycleScope.launch {
                    val response = endpoint.submitCartItems(order)
                    if (response.isSuccessful) {
                        // Handle successful response
                        Toast.makeText(requireContext(), "Cart items submitted successfully", Toast.LENGTH_SHORT).show()

                        // Clear the cart or perform any other necessary actions
                        lifecycleScope.launch {
                            cartItemModel.emptyCart()
                            updateUi()
                            binding.cartRecyclerView.adapter?.notifyDataSetChanged()
                        }
                    } else {
                        // Handle error response
                        Toast.makeText(requireContext(), "Failed to submit cart items", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            else{
                Toast.makeText(requireContext(), "Please put items in your cart before validating your order", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun updateUi() {
        binding.cartItemCountTextView.text = (binding.cartRecyclerView.adapter as CartAdapter).itemCount.toString()
        binding.cartDialogItemTotalTextView.text = cartItemModel.getTotal().toString() + " DZD"
        if (cartItemModel.restaurantId!=-1){
            val total = cartItemModel.getTotal()
            val restaurantFee = cartItemModel.restaurantFee ?: 0.0
            val totalPrice = (total + restaurantFee)
            binding.cartDialogDeliveryFeeTextView.text= "$restaurantFee DZD"
            binding.cartDialogTotalTextView.text= "$totalPrice DZD"
        }else{
            binding.cartDialogDeliveryFeeTextView.text="0 DZD"
            binding.cartDialogTotalTextView.text="0 DZD"
        }

    }
}
