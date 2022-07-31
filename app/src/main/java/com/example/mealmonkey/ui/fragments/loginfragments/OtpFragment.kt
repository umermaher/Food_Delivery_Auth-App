package com.example.mealmonkey.ui.fragments.loginfragments

import android.content.Intent
import android.content.IntentFilter
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
import com.example.mealmonkey.receivers.OtpReceiver
import com.example.mealmonkey.ui.activities.MainActivity
import com.example.mealmonkey.utils.NavigationUtils
import com.example.mealmonkey.utils.Resource
import com.example.mealmonkey.viewmodels.UserViewModel
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class OtpFragment : Fragment() {
    private var _binding:FragmentOtpBinding?=null
    private val binding get() = _binding!!
    private val args:OtpFragmentArgs by navArgs()
    private lateinit var verificationId: String
    private val viewModel:UserViewModel by viewModels()
    private lateinit var otpReceiver: OtpReceiver
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentOtpBinding.inflate(layoutInflater)

//        user=args.user

        binding.infoText.text="Please check your mobile number ${args.phoneNo}\n continue to reset your password"
        verificationId=args.verificationId

        binding.nextBtn.setOnClickListener { 
            val code=binding.pinView.text.toString()
            if(code.isNotEmpty() && code.length==6){
                verifyCode(verificationId,code)
            }
        }

        binding.resendCodeText.setOnClickListener {
            resendOtp()
        }

        autoOtpReciever()

        return binding.root
    }

    private fun autoOtpReciever() {
        otpReceiver= OtpReceiver(createOtpRecieverListener())
        requireActivity().registerReceiver(otpReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
    }

    private fun createOtpRecieverListener(): OtpReceiver.OtpReceiverListener = object : OtpReceiver.OtpReceiverListener{
        override fun onOtpSuccess(otp: String) {
            binding.pinView.setText(otp)
            verifyCode(verificationId,otp)
        }

        override fun onOtpTimeOut() {
            Toast.makeText(requireContext(),"Something went wrong! Please try again..",Toast.LENGTH_LONG).show()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun verifyCode(verificationId:String,code: String) {
        viewModel.verifyCode(verificationId,code)
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
                            Toast.makeText(requireContext(),"Done!",Toast.LENGTH_SHORT).show()
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

    private fun resendOtp() {
        viewModel.otpSend(requireActivity(),args.email,args.phoneNo)
        viewModel.sendOtpResponse.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Loading -> {
                    requireActivity().runOnUiThread {
                        binding.otpPb.visibility = View.VISIBLE
                        binding.otpPb.animate().alpha(1f).duration=800
                        binding.nextBtn.isEnabled=false
                    }
                }
                is Resource.Success->{
                    GlobalScope.launch {
                        delay(1000L)
                        withContext(Dispatchers.Main) {
                            binding.otpPb.animate().alpha(0f).duration = 800
                            binding.nextBtn.isEnabled=true
                            verificationId=response.data!!
                        }
                    }
                }
                is Resource.Error -> {
                    GlobalScope.launch {
                        delay(1000L)
                        withContext(Dispatchers.Main) {
                            binding.otpPb.animate().alpha(0f).duration = 800
                            Snackbar.make(binding.root, response.message!!, Snackbar.LENGTH_LONG)
                                .show()
                            binding.nextBtn.isEnabled = true
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
