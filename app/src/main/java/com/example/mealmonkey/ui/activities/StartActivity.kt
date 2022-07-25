package com.example.mealmonkey.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mealmonkey.databinding.ActivityStartBinding
import com.example.mealmonkey.utils.setStatusBarTransparent
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount


class StartActivity : AppCompatActivity() {
    private lateinit var binding:ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStartBinding.inflate(layoutInflater)
        setStatusBarTransparent(this,binding.root)
        setContentView(binding.root)
    }


    fun login(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
    fun createAccount(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        if(isLoggedIn)
            updateUI(isLoggedInWithFb = isLoggedIn)
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun updateUI(account: GoogleSignInAccount? = null,isLoggedInWithFb:Boolean=false) {
        if(account!=null || isLoggedInWithFb){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}