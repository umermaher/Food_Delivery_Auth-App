package com.example.mealmonkey.utils

import android.widget.EditText

object TextValidations {
    fun validateEmail(emailText:EditText): Boolean{
        val value=emailText.text.toString()
        val test:Boolean= android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()

        return if(value.isEmpty()){
            emailText.error = "Fields cannot be empty"
            false
        }else if (!test){
            emailText.error="Invalid email address"
            false
        }else{
            emailText.error = null;
            true
        }
    }

    fun validatePassword(passwordText: EditText):Boolean{
        val value=passwordText.text.toString()
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$"

        return if(value.isEmpty()){
            passwordText.error = "Fields cannot be empty"
            false
        }else if(!value.matches(pattern.toRegex())){
            passwordText.error = "Password is too weak"
            false
        }else{
            passwordText.error = null
//            passwordText.isErrorEnabled = false
            true
        }
    }

    fun validateFullName(fullNameText:EditText): Boolean {
        val value = fullNameText.text.toString()
        return if (value.isEmpty()) {
            fullNameText.error = "Fields cannot be empty"
            false
        }else if(value.matches(".*\\d.*".toRegex())){
            fullNameText.error="Remove numbers!"
            false
        } else {
            fullNameText.error = null
//            fullNameText.isErrorEnabled = false
            true
        }
    }
}