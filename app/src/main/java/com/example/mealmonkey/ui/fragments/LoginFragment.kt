package com.example.mealmonkey.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentLoginBinding
import com.example.mealmonkey.ui.activities.SignUpActivity
import com.example.mealmonkey.utils.TextValidations.validateEmail
import com.example.mealmonkey.utils.TextValidations.validatePassword
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentLoginBinding.inflate(layoutInflater)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        binding.loginBtn.setOnClickListener {
            customSignIn()
        }

        binding.fbSignInBtn.setOnClickListener {

        }

        binding.googleSignInBtn.setOnClickListener{

        }

        binding.signUpText.setOnClickListener {
            val intent=Intent(requireContext(),SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        return binding.root
    }

    private fun customSignIn() {
        val test1= validateEmail(binding.emailEditText)
        val test2= validatePassword(binding.passwordEditText)
        when(false){
            test1 -> return
            test2 -> return
            else -> {}
        }
        Toast.makeText(requireContext(),"hell",Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        this._binding=null
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                activity?.onBackPressed()
//            }
//        })
//    }
}