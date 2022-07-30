package com.example.mealmonkey.ui.fragments.loginfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentResetPasswordBinding
import com.example.mealmonkey.utils.NavigationUtils
import com.example.mealmonkey.utils.Resource
import com.example.mealmonkey.utils.TextValidations.validatePassword
import com.example.mealmonkey.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {
    private var _binding:FragmentResetPasswordBinding?=null
    private val binding get() = _binding!!
    private val viewModel:UserViewModel by viewModels()
    private val args:ResetPasswordFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentResetPasswordBinding.inflate(layoutInflater)

        binding.nextBtn.setOnClickListener {
            resetPassword()
        }

        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun resetPassword() {
        val test1=validatePassword(binding.passwordEditText)
        val test2=validatePassword(binding.confirmPasswordEditText)
        val bothAreSame=binding.passwordEditText.text.toString()==binding.confirmPasswordEditText.text.toString()
        when(false){
            test1-> return
            test2-> return
            bothAreSame->{
                binding.confirmPasswordEditText.error="Confirm Password correctly!"
                return
            }
            else -> {}
        }

        val password=binding.passwordEditText.text.toString()
        viewModel.changePassword(args.email,password)
        viewModel.changePasswordResponse.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Loading->{
                    requireActivity().runOnUiThread {
                        binding.resetPb.visibility = View.VISIBLE
                        binding.resetPb.animate().alpha(1f).duration = 800
                        binding.nextBtn.isEnabled = false
                    }
                }
                is Resource.Success->{
                    GlobalScope.launch {
                        delay(1000L)
                        withContext(Dispatchers.Main) {
                            binding.resetPb.animate().alpha(0f).duration = 800
                            binding.nextBtn.isEnabled=true
                            Toast.makeText(requireContext(), response.data!!.message,Toast.LENGTH_SHORT).show()
                            NavigationUtils.navigateSafe(findNavController(), R.id.action_resetPasswordFragment_to_loginFragment, null)

                        }
                    }
                }
                is Resource.Error->{
                    GlobalScope.launch {
                        delay(1000L)
                        withContext(Dispatchers.Main) {
                            binding.resetPb.animate().alpha(0f).duration = 800
                            Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_LONG)
                                .show()
                            binding.nextBtn.isEnabled = true
                        }
                    }
                }
            }
        }
    }

}