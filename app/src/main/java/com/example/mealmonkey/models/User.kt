package com.example.mealmonkey.models

data class User (
    val name:String,val email:String,
    val photoUrl:String="-", val phoneNo:String="-",
    val address:String="-",val password:String="-"
)