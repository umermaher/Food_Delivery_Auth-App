package com.example.mealmonkey.di

import android.app.Application
import com.example.mealmonkey.apis.UserApi
import com.example.mealmonkey.repositories.UserRepository
import com.example.mealmonkey.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserApi():UserApi{
        val retrofit by lazy {
            val logging = HttpLoggingInterceptor()

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .build()
        }
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api:UserApi,app: Application):UserRepository{
        return UserRepository(api,app)
    }
}