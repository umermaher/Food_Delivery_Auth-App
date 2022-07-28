package com.example.mealmonkey.models

import java.io.Serializable
import java.lang.Error

data class User (
    val name:String,val email:String,
    val photoUrl:String="-", val phoneNo:String="-",
    val address:String="-",val password:String="-"
):Serializable