package com.example.tdm.loginApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.tdm.R
import com.example.tdm.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    lateinit var binding: FragmentWelcomeBinding
//    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sessionManager = SessionManager(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (sessionManager.isLoggedIn) {
//            navigateToMainActivity()
//            return
//        }
        binding.signUpBtn.setOnClickListener(){
                view: View ->view.findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }

        binding.signInBtn.setOnClickListener(){
                view: View ->view.findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

    }
//    private fun navigateToMainActivity() {
//        view?.findNavController()?.navigate(R.id.action_welcomeFragment_to_mainActivity)
//    }
}