package com.example.mealmonkey.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mealmonkey.databinding.ActivityMainBinding
import com.example.mealmonkey.utils.Constants.Companion.CUSTOM_LOGIN
import com.example.mealmonkey.utils.Constants.Companion.FACEBOOK_LOGIN
import com.example.mealmonkey.utils.Constants.Companion.GOOGLE_LOGIN
import com.example.mealmonkey.utils.PrefsData
import com.example.mealmonkey.utils.setStatusBarTransparent
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener


class MainActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTransparent(this,binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        configureGoogleSignIn()
        val user=PrefsData(this).getUser()

        binding.userDataText.text=user.email+" "+user.name+" "+PrefsData(this).isLoggedIn()+" "+PrefsData(this).getLoginType()

    }

    private fun configureGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
    }

    fun logout(view: View) {
        when(PrefsData(this).getLoginType()){
            CUSTOM_LOGIN-> updateUI()
            FACEBOOK_LOGIN-> disconnectFromFacebook()
            GOOGLE_LOGIN -> {
                mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, OnCompleteListener<Void?> {
                        updateUI()
                    })
            }
        }
    }

    private fun updateUI() {
        PrefsData(this).clearUser()
        startActivity(Intent(this,StartActivity::class.java))
        finish()
    }

    private fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return  // already logged out
        }
        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/permissions/",
            null,
            HttpMethod.DELETE,
            GraphRequest.Callback {
                LoginManager.getInstance().logOut()
                updateUI()
            }).executeAsync()
    }
}