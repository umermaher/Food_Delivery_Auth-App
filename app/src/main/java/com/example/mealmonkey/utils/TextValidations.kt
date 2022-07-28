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

    fun validatePhoneNo(phoneNoText:EditText): Boolean {
        val value: String = phoneNoText.text.toString()
        return if (value.isEmpty()) {
            phoneNoText.error = "Fields cannot be empty"
            false
        }else if(value.length<=5){
            phoneNoText.error = "Invalid Mobile No."
            false
        } else {
            phoneNoText.error = null
//            phoneNoText.setErrorEnabled(false)
            true
        }
    }
    fun validateAddress(text:EditText):Boolean{
        val value:String=text.text.toString()
        return if(value.isEmpty()){
            text.error="Fields cannot be empty"
            false
        }else if(value.length<=10){
            text.error="I think, your address is not cleared, is it?"
            false
        }else{
            text.error=null
            true
        }
    }
}