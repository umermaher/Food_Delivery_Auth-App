package com.example.mealmonkey.ui.fragments.loginfragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mealmonkey.databinding.FragmentOtpBinding
import com.example.mealmonkey.models.User
import com.example.mealmonkey.ui.activities.MainActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OtpFragment : Fragment() {
    private var _binding:FragmentOtpBinding?=null
    private val binding get() = _binding!!
    private val args:OtpFragmentArgs by navArgs()
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
//                verifyCode(code)
            }
        }
        return binding.root
    }

//    private fun verifyCode(code: String) {
//        binding.nextBtn.isEnabled=false
//        binding.otpPb.visibility=View.VISIBLE
//
//        val credential = PhoneAuthProvider.getCredential(args.verificationId, code)
//        Firebase.auth.signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    binding.nextBtn.isEnabled=true
//                    binding.otpPb.visibility=View.GONE
//                    val intent=Intent(requireContext(),MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    activity?.finish()
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                        Toast.makeText(requireContext(), (task.exception as FirebaseAuthInvalidCredentialsException).message,Toast.LENGTH_LONG).show()
//                    }
//                    // Update UI
//                }
//            }
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}
