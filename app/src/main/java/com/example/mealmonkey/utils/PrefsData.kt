package com.example.mealmonkey.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.mealmonkey.models.User

class PrefsData(val context: Context) {
    companion object{
        const val USER_DATA="userData"
        const val USER_NAME="name"
        const val USER_EMAIL="email"
        const val USER_PHOTO_URL="photoUrl"
        const val USER_PHONE_N0="phoneNo"
        const val USER_ADDRESS="address"
        const val IS_CUSTOM_LOGGED_IN="isCustomLoggedIn"
        const val LOGIN_TYPE="loginType"
        const val IS_FIRST_TIME="isFirstTime"
    }
    fun saveUser(user: User){
        val sp=context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE)
        val editor=sp.edit()
        editor.putString(USER_NAME,user.name)
        editor.putString(USER_EMAIL,user.email)
        editor.putString(USER_PHOTO_URL,user.photoUrl)
        editor.putString(USER_PHONE_N0,user.phoneNo)
        editor.putString(USER_ADDRESS,user.address)
        editor.putBoolean(IS_CUSTOM_LOGGED_IN,true)
        editor.apply()
    }
    fun getUser():User{
        val sp=context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE)
        return User(
            sp.getString(USER_NAME,"-")?:"-",
            sp.getString(USER_EMAIL,"-")?:"-",
            sp.getString(USER_PHOTO_URL,"-")?:"-",
            sp.getString(USER_PHONE_N0,"-")?:"-",
            sp.getString(USER_ADDRESS,"-")?:"-",
        )
    }
    fun yesLoggedIn() = context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE).edit().putBoolean(IS_CUSTOM_LOGGED_IN,true).apply()

    fun isLoggedIn(): Boolean=context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE).getBoolean(
        IS_CUSTOM_LOGGED_IN,false)

    fun clearUser(){
        val sp=context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE)
        val editor=sp.edit()
        editor.clear()
        .apply()
        yesFirstTime()
    }

    fun saveLoginType(value:Int) = context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE)
        .edit().putInt(LOGIN_TYPE,value).apply()
    fun getLoginType():Int = context.getSharedPreferences(USER_DATA,Context.MODE_PRIVATE).getInt(
        LOGIN_TYPE,10)

    fun yesFirstTime() {
        context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .edit().putBoolean(IS_FIRST_TIME, false).apply()
    }
    fun isFirstTime() = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).getBoolean(IS_FIRST_TIME,true)
}