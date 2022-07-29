package com.example.mealmonkey.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mealmonkey.databinding.ActivityStartBinding
import com.example.mealmonkey.utils.PrefsData
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

    fun createAccount(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        if(PrefsData(this).isLoggedIn()) updateUI()
    }

    private fun updateUI() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun loginActivity(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}