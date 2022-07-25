package com.example.mealmonkey.repositories

import com.example.mealmonkey.RetrofitInstance
import com.example.mealmonkey.models.User

class UserRepository {

    suspend fun registerUser(user:User) = RetrofitInstance.userApi.registerUser(
        user.name,user.email,user.photoUrl,user.phoneNo,user.address,user.password
    )
}