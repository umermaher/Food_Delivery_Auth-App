package com.example.mealmonkey.repositories

import android.app.Application
import com.example.mealmonkey.apis.UserApi
import com.example.mealmonkey.models.User

class UserRepository (val userApi:UserApi,val app:Application){

    suspend fun registerUser(user:User) = userApi.registerUser(
        user.name,user.email,user.photoUrl,user.phoneNo,user.address,user.password
    )
    suspend fun loginUser(email:String,password:String) = userApi.loginUser(email,password)

    suspend fun getUserByEmail(tableName:String,email: String) = userApi.getUserByEmail(tableName,email)
}