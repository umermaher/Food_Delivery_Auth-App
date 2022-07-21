package com.example.mealmonkey.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mealmonkey.databinding.ActivityMainBinding
import com.example.mealmonkey.utils.setStatusBarTransparent

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTransparent(this,binding.root)
    }

    fun login(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }
    fun createAccount(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
    }
    private fun hideStatusBar() {
        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
    }
}