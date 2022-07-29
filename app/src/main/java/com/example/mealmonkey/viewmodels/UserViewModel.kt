package com.example.mealmonkey.viewmodels

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mealmonkey.MealMonkeyApplication
import com.example.mealmonkey.models.RegisterUserResponse
import com.example.mealmonkey.models.User
import com.example.mealmonkey.repositories.UserRepository
import com.example.mealmonkey.ui.activities.MainActivity
import com.example.mealmonkey.utils.Constants.Companion.USER_TABLE_NAME
import com.example.mealmonkey.utils.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val userRepository: UserRepository):AndroidViewModel(userRepository.app) {
    val regUserResponse = MutableLiveData<Resource<RegisterUserResponse>>()
    val loginUserResponse=MutableLiveData<Resource<RegisterUserResponse>>()
    val getUserResponse=MutableLiveData<Resource<User>>()
    val loginUserWithOtherAccountResponse= MutableLiveData<Resource<User>>()
    val sendOtpResponse=MutableLiveData<Resource<String>>()

    fun loginUser(email:String,password:String)=viewModelScope.launch {
        getUserResponse.value=Resource.Loading()
        if(hasInternetConnection()){
            try{
                val response=userRepository.loginUser(email,password)
                getUserResponse.value=handleLoginUser(response,email)
            }catch (e:Exception){
                getUserResponse.value=Resource.Error("Failed to login: ${e.message}")
            }
        }else{
            getUserResponse.value=Resource.Error("No Internet Connection")
        }
    }

    private suspend fun handleLoginUser(response: Response<RegisterUserResponse>, email: String): Resource<User>? {
        return if(response.isSuccessful){
            if(response.body()!!.error){
                Resource.Error(response.body()!!.message)
            }else{
                getUserByEmail(email)
            }
        }else Resource.Error(response.message())
    }

    private suspend fun getUserByEmail(email: String): Resource<User>? {
        return if(hasInternetConnection()){
            try{
                val response=userRepository.getUserByEmail(USER_TABLE_NAME,email)
                if(response.isSuccessful)
                    Resource.Success(response.body()!!)
                else Resource.Error(response.message())
            }catch (e:Exception){
                Resource.Error("Something went wrong: ${e.message}")
            }
        }else{
            Resource.Error("No Internet Connection")
        }
    }
    fun registerUser(user: User) = viewModelScope.launch {
        regUserResponse.value=Resource.Loading()
        if(hasInternetConnection()){
            try{
                val response=userRepository.registerUser(user)
                regUserResponse.value=handleRegisterUser(response)
            }catch (e: Exception){
                regUserResponse.value=Resource.Error("Failed to register: ${e.message}")
            }
        }else{
            regUserResponse.value=Resource.Error("No Internet Connection")
        }
    }

    private fun handleRegisterUser(response: Response<RegisterUserResponse>): Resource<RegisterUserResponse>? {
        return if(response.isSuccessful) {
            if(response.body()!!.error){
                Resource.Error(response.body()!!.message)
            }else
                Resource.Success(response.body()!!)
        }
        else
            Resource.Error(response.message())
    }

    fun loginUserWithOtherAccount(user: User)= viewModelScope.launch{
        loginUserWithOtherAccountResponse.value=Resource.Loading()
        if(hasInternetConnection()){
            try{
                val response=userRepository.loginUserWithOtherAccount(user)
                if(response.isSuccessful){
                    loginUserWithOtherAccountResponse.value=Resource.Success(response.body()!!)
                }else loginUserWithOtherAccountResponse.value=Resource.Error("Something went wrong: ${response.message()}")
            }catch (e:Exception){
                loginUserWithOtherAccountResponse.value=Resource.Error("Something went wrong: ${e.message}")
            }
        }else
            loginUserWithOtherAccountResponse.value=Resource.Error("No Internet Connection")
    }

    private suspend fun isEmailExistWithPhoneNo(email: String, phoneNo:String):Boolean{
        sendOtpResponse.value=Resource.Loading()
        return if(hasInternetConnection()){
            try{
                val response=userRepository.isEmailExistWithPhoneNo(email,phoneNo)
                if(response.isSuccessful){
                    if(response.body()!!.error) {
                        sendOtpResponse.value=Resource.Error(response.body()!!.message)
                        false
                    }
                    else {
                        sendOtpResponse.value=Resource.Success(response.body()!!.message)
                        true
                    }
                }else{
                    sendOtpResponse.value=Resource.Error(response.message())
                    false
                }
            }catch (e:Exception){
                sendOtpResponse.value=Resource.Error("Error: ${e.message}")
                false
            }
        }else{
            sendOtpResponse.value=Resource.Error("No Internet Connection")
            false
        }
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mCallback:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    fun otpSend(activity: Activity,email: String,phoneNo: String) =viewModelScope.launch {
        if(isEmailExistWithPhoneNo(email,phoneNo)){

        }
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
    }

    private fun hasInternetConnection():Boolean{
        val connectivityManager=getApplication<MealMonkeyApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork= connectivityManager.activeNetwork ?: return false
            val networkCapabilities=connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                networkCapabilities.hasTransport(TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> true
                }
            }
        }
        return false
    }
}