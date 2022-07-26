package com.example.mealmonkey.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentOtpBinding
import com.example.mealmonkey.utils.Resource
import com.google.android.material.snackbar.Snackbar

class OtpFragment : Fragment() {
    private var _binding:FragmentOtpBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentOtpBinding.inflate(layoutInflater)
        return binding.root
    }
}
//viewModel.registerUser(
//User(
//binding.nameEditText.text.toString(),
//binding.emailEditText.text.toString(),
//phoneNo = binding.phoneEditText.text.toString(),
//address = binding.addressEditText.text.toString(),
//password = binding.passwordEditText.text.toString()
//)
//)
//viewModel.regUserResponse.observe(viewLifecycleOwner) {
//    when (it) {
//        is Resource.Loading -> binding.signUpPb.visibility = View.VISIBLE
//        is Resource.Success -> {
//            binding.signUpPb.visibility = View.GONE
//            Toast.makeText(requireContext(), "Sign up successful!", Toast.LENGTH_LONG)
//                .show()
//            findNavController().navigate(R.id.action_signUpFragment_to_otpFragment)
//        }
//        is Resource.Error -> {
//            binding.signUpPb.visibility = View.GONE
//            Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
//        }
//    }
//}