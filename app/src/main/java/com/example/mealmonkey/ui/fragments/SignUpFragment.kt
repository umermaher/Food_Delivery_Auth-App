package com.example.mealmonkey.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentSignUpBinding
import com.example.mealmonkey.models.User
import com.example.mealmonkey.utils.Resource
import com.example.mealmonkey.utils.TextValidations
import com.example.mealmonkey.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(layoutInflater)

        binding.loginText.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.SignUpBtn.setOnClickListener {
            signUp()
//            findNavController().navigate(R.id.action_signUpFragment_to_otpFragment)
        }

        return binding.root
    }

    private fun signUp() {
        val test1 = TextValidations.validateFullName(binding.nameEditText)
        val test2 = TextValidations.validateEmail(binding.emailEditText)
        val test3 = TextValidations.validatePhoneNo(binding.phoneEditText)
        val test4 = TextValidations.validatePhoneNo(binding.addressEditText)
        val test5 = TextValidations.validatePassword(binding.passwordEditText)
        val test6 = TextValidations.validatePassword(binding.confirmPasswordEditText)
        val bothPasswordSame: Boolean =
            binding.confirmPasswordEditText.text.toString() == binding.passwordEditText.text.toString()
        when (false) {
            test1 -> return
            test2 -> return
            test3 -> return
            test4 -> return
            test5 -> return
            test6 -> return
            bothPasswordSame -> {
                binding.confirmPasswordEditText.error = "Confirm your password correctly!"
                return
            }
            else -> {}
        }

        val user = User(
            binding.nameEditText.text.toString(),
            binding.emailEditText.text.toString(),
            phoneNo = binding.phoneEditText.text.toString(),
            address = binding.addressEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )
        viewModel.isUserExist(user.email)
        viewModel.userExistResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.signUpPb.visibility = View.VISIBLE

                is Resource.Success -> {
                    binding.signUpPb.visibility = View.GONE
                    if (it.data!!.error) {
                        binding.emailEditText.error = it.data.message
                    } else {
                        findNavController().navigate(R.id.action_signUpFragment_to_otpFragment)
                    }
                }
                is Resource.Error -> {
                    binding.signUpPb.visibility = View.GONE
                    Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}