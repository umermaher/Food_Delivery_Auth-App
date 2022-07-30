package com.example.mealmonkey.apis

import com.example.mealmonkey.models.RegisterUserResponse
import com.example.mealmonkey.models.User
import com.example.mealmonkey.utils.Constants.Companion.CHANGE_PASSWORD
import com.example.mealmonkey.utils.Constants.Companion.DOES_USER_EXIST
import com.example.mealmonkey.utils.Constants.Companion.GET_USER_BY_EMAIL
import com.example.mealmonkey.utils.Constants.Companion.IS_EMAIL_EXIST_WITH_PHONE_NO
import com.example.mealmonkey.utils.Constants.Companion.LOGIN_WITH_OTHER_ACCOUNT
import com.example.mealmonkey.utils.Constants.Companion.REG_USER_SITE_URL
import com.example.mealmonkey.utils.Constants.Companion.USER_LOGIN_URL
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @FormUrlEncoded
    @POST(REG_USER_SITE_URL)
    suspend fun registerUser(
        @Field("name")name:String,
        @Field("email")email:String,
        @Field("photoUrl")photoUrl:String,
        @Field("phoneNo")phoneNo:String,
        @Field("address")address:String,
        @Field("password")password:String,
    ):Response<RegisterUserResponse>

    @FormUrlEncoded
    @POST(USER_LOGIN_URL)
    suspend fun loginUser(
        @Field("email")email:String,
        @Field("password")password:String
    ):Response<RegisterUserResponse>

    @FormUrlEncoded
    @POST(GET_USER_BY_EMAIL)
    suspend fun getUserByEmail(
        @Field("tableName")tableName:String,
        @Field("email")email:String
    ):Response<User>

    @FormUrlEncoded
    @POST(LOGIN_WITH_OTHER_ACCOUNT)
    suspend fun loginUserWithOtherAccount(
        @Field("name")name:String,
        @Field("email")email:String,
        @Field("photoUrl")photoUrl:String,
        @Field("phoneNo")phoneNo:String,
        @Field("address")address:String,
    ):Response<User>
    @FormUrlEncoded
    @POST(IS_EMAIL_EXIST_WITH_PHONE_NO)
    suspend fun isEmailExistWithPhoneNo(
        @Field("email")email:String,
        @Field("phoneNo")phoneNo:String
    ):Response<RegisterUserResponse>

    @FormUrlEncoded
    @POST(CHANGE_PASSWORD)
    suspend fun changePassword(
        @Field("email")email:String,
        @Field("password")password:String
    ):Response<RegisterUserResponse>
//    @FormUrlEncoded
//    @POST(DOES_USER_EXIST)
//    suspend fun isUserExist(
//        @Field("email")email: String
//    ):Response<RegisterUserResponse>
}