package com.example.mealmonkey.utils

class Constants {
    companion object{
        const val BASE_URL="http://192.168.100.8/meal_monkey/v1/"
        const val REG_USER_SITE_URL="registerUser.php"
        const val REG_USER_URL= BASE_URL+REG_USER_SITE_URL
        const val DOES_USER_EXIST="isUserExist.php"
        const val USER_LOGIN_URL="loginUser.php"
        const val GET_USER_BY_EMAIL="getUserByEmail.php"
        const val IS_EMAIL_EXIST_WITH_PHONE_NO="isEmailExistWithPhoneNo.php"
        const val CHANGE_PASSWORD="changePassword.php"

        //google, facebook
        const val LOGIN_WITH_OTHER_ACCOUNT="loginUserWithOtherAccount.php"
        const val USER_TABLE_NAME="tbl_user"

        const val GOOGLE_LOGIN=3
        const val FACEBOOK_LOGIN=2
        const val CUSTOM_LOGIN=1

        const val INVALID_PASSWORD="Invalid password!";
        const val USER_EXIST_MESSAGE="User with this email already exist!"
    }
}