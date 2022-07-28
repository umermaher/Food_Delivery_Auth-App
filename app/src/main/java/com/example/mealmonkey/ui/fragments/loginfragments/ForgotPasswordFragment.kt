package com.example.mealmonkey.ui.fragments.loginfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentForgotPasswordBinding
import com.example.mealmonkey.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding?=null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mCallback:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentForgotPasswordBinding.inflate(layoutInflater)

        return binding.root
    }
//    private fun otpSend() {
//        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
//            override fun onVerificationFailed(e: FirebaseException) {
//                binding.signUpPb.visibility = View.GONE
//                binding.SignUpBtn.isEnabled=true
//                Toast.makeText(this@SignUpActivity, "Verification failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                binding.signUpPb.visibility = View.GONE
//                binding.SignUpBtn.isEnabled=true
//            }
//        }
//
//        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
//            .setPhoneNumber(user.phoneNo)       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this)                 // Activity (for callback binding)
//            .setCallbacks(mCallback)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
}