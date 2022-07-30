package com.example.mealmonkey.ui.fragments.loginfragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentOtpBinding
import com.example.mealmonkey.models.User
import com.example.mealmonkey.ui.activities.MainActivity
import com.example.mealmonkey.utils.NavigationUtils
import com.example.mealmonkey.utils.Resource
import com.example.mealmonkey.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class OtpFragment : Fragment() {
    private var _binding:FragmentOtpBinding?=null
    private val binding get() = _binding!!
    private val args:OtpFragmentArgs by navArgs()
    private val viewModel:UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentOtpBinding.inflate(layoutInflater)

//        user=args.user

        binding.infoText.text="Please check your mobile number ${args.phoneNo}\n continue to reset your password"
        
        binding.nextBtn.setOnClickListener { 
            val code=binding.pinView.text.toString()
            if(code.isNotEmpty() && code.length==6){
                verifyCode(code)
            }
        }
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun verifyCode(code: String) {
        binding.nextBtn.isEnabled=false
        binding.otpPb.visibility=View.VISIBLE

        viewModel.verifyCode(args.verificationId,code)
        viewModel.verifyCodeResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading->{
                    requireActivity().runOnUiThread {
                        binding.otpPb.visibility = View.VISIBLE
                        binding.otpPb.animate().alpha(1f).duration = 800
                        binding.nextBtn.isEnabled = false
                    }
                }
                is Resource.Success->{
                    GlobalScope.launch {
                        delay(1000L)
                        withContext(Dispatchers.Main) {
                            binding.nextBtn.isEnabled = true
                            binding.otpPb.animate().alpha(0f).duration = 800
                            val bundle=Bundle()
                            bundle.putString("email",args.email)
                            val navController: NavController = Navigation.findNavController(view!!)
                            NavigationUtils.navigateSafe(findNavController(), R.id.action_otpFragment_to_resetPasswordFragment, bundle)
                        }
                    }
                }
                is Resource.Error->{
                    GlobalScope.launch {
                        delay(1000L)
                        withContext(Dispatchers.Main) {
                            binding.nextBtn.isEnabled = true
                            binding.otpPb.animate().alpha(0f).duration = 800
                            Toast.makeText(requireContext(),"Error: ${it.message}",Toast.LENGTH_LONG).show()
                        }
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
