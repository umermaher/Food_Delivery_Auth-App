package com.example.mealmonkey.ui.fragments.loginfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentForgotPasswordBinding
import com.example.mealmonkey.utils.NavigationUtils
import com.example.mealmonkey.utils.Resource
import com.example.mealmonkey.utils.TextValidations
import com.example.mealmonkey.utils.TextValidations.validateEmail
import com.example.mealmonkey.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding?=null
    private val binding get() = _binding!!
    private val viewModel:UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentForgotPasswordBinding.inflate(layoutInflater)

        binding.sendBtn.setOnClickListener {
            checkInputs()
        }

        return binding.root
    }

    private fun checkInputs() {
        val test1= validateEmail(binding.emailEditText)
        val test2= TextValidations.validatePhoneNo(binding.phoneEditText)
        when(false){
            test1 -> return
            test2 -> return
            else -> {}
        }
        val phoneNo=binding.countryCodePicker.selectedCountryCodeWithPlus + binding.phoneEditText.text.toString().trim()
        val email=binding.emailEditText.text.toString()
        viewModel.otpSend(requireActivity(),email,phoneNo)
        viewModel.sendOtpResponse.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Loading -> {
                    binding.forgotPb.visibility = View.VISIBLE
                    binding.forgotPb.animate().alpha(1f).duration=800
                    binding.forgotPb.isEnabled=false
                }
                is Resource.Success->{
                    lifecycleScope.launch {
                        delay(1000L)
                        binding.forgotPb.animate().alpha(0f).duration = 800
                        Snackbar.make(binding.root, response.data!!,Snackbar.LENGTH_LONG).show()
//                        val directions=ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToOtpFragment(phoneNo,email,response.data)
                        val bundle=Bundle()
                        bundle.putString("phoneNo",phoneNo)
                        bundle.putString("verificationId",response.data)
                        bundle.putString("email",email)

                        //I was getting error that nav cannot find destination
                        //I use this navigateSafe
                        val navController: NavController = Navigation.findNavController(view!!)
                        NavigationUtils.navigateSafe(navController, R.id.action_forgotPasswordFragment_to_otpFragment, bundle);

                    }
                }
                is Resource.Error -> {
                    lifecycleScope.launch {
                        delay(1000L)
                        binding.forgotPb.animate().alpha(0f).duration = 800
                        Snackbar.make(binding.root, response.message!!,Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}