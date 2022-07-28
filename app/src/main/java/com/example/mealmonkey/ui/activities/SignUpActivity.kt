package com.example.mealmonkey.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mealmonkey.databinding.ActivitySignUpBinding
import com.example.mealmonkey.models.User
import com.example.mealmonkey.utils.Constants.Companion.USER_EXIST_MESSAGE
import com.example.mealmonkey.utils.PrefsData
import com.example.mealmonkey.utils.Resource
import com.example.mealmonkey.utils.TextValidations
import com.example.mealmonkey.utils.setStatusBarTransparent
import com.example.mealmonkey.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var user:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarTransparent(this,binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        binding.SignUpBtn.setOnClickListener {
            signUp()
//            findNavController().navigate(R.id.action_signUpFragment_to_otpFragment)
        }
    }

    private fun signUp() {
        val test1 = TextValidations.validateFullName(binding.nameEditText)
        val test2 = TextValidations.validateEmail(binding.emailEditText)
        val test3 = TextValidations.validatePhoneNo(binding.phoneEditText)
        val test4 = TextValidations.validateAddress(binding.addressEditText)
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

        user = User(
            binding.nameEditText.text.toString(),
            binding.emailEditText.text.toString(),
            phoneNo = binding.countryCodePicker.selectedCountryCodeWithPlus + binding.phoneEditText.text.toString().trim(),
            address = binding.addressEditText.text.toString(),
            password = binding.passwordEditText.text.toString()
        )

        viewModel.registerUser(user)
        viewModel.regUserResponse.observe(this){ response ->
            when(response){
                is Resource.Loading -> {
                    binding.signUpPb.visibility = View.VISIBLE
                    binding.SignUpBtn.isEnabled=false
                }
                is Resource.Success -> {
                    binding.signUpPb.visibility = View.GONE
                    binding.SignUpBtn.isEnabled=true
                    PrefsData(this).
                    startActivity(Intent(this@SignUpActivity,MainActivity::class.java))
                }
                is Resource.Error -> {
                    binding.signUpPb.visibility = View.GONE
                    binding.SignUpBtn.isEnabled=true
                    if(response.message == USER_EXIST_MESSAGE){
                            binding.emailEditText.error = response.message
                    } else
                        Snackbar.make(binding.root, "${response.message}", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    fun login(view: View) { onBackPressed() }
}