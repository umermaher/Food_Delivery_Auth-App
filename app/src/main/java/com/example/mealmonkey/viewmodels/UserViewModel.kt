package com.example.mealmonkey.viewmodels

import android.app.Application
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
import com.example.mealmonkey.utils.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

class UserViewModel(val userRepository: UserRepository,app:Application):AndroidViewModel(app) {
    val regUserResponse= MutableLiveData<Resource<RegisterUserResponse>>()

    fun registerUser(user: User) = viewModelScope.launch {
        
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