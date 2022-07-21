package com.example.mealmonkey.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding:FragmentSignUpBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentSignUpBinding.inflate(layoutInflater)


        binding.loginText.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.SignUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_otpFragment)
        }

        return binding.root
    }

}