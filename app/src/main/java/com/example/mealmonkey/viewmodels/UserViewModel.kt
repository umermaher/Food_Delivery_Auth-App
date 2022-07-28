package com.example.mealmonkey.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mealmonkey.MealMonkeyApplication
import com.example.mealmonkey.models.RegisterUserResponse
import com.example.mealmonkey.models.User
import com.example.mealmonkey.repositories.UserRepository
import com.example.mealmonkey.utils.Constants.Companion.USER_TABLE_NAME
import com.example.mealmonkey.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val userRepository: UserRepository):AndroidViewModel(userRepository.app) {
    val regUserResponse = MutableLiveData<Resource<RegisterUserResponse>>()
    val loginUserResponse=MutableLiveData<Resource<RegisterUserResponse>>()
    val getUserResponse=MutableLiveData<Resource<User>>()

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
            return try{
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