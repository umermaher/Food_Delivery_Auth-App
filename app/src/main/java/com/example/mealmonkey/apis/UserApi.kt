package com.example.mealmonkey.apis

import com.example.mealmonkey.models.RegisterUserResponse
import com.example.mealmonkey.utils.Constants.Companion.DOES_USER_EXIST
import com.example.mealmonkey.utils.Constants.Companion.REG_USER_SITE_URL
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
        @Field("photourl")photoUrl:String,
        @Field("phoneno")phoneNo:String,
        @Field("address")address:String,
        @Field("password")password:String,
    ):Response<RegisterUserResponse>

    @FormUrlEncoded
    @POST(DOES_USER_EXIST)
    suspend fun isUserExist(
        @Field("email")email: String
    ):Response<RegisterUserResponse>
}