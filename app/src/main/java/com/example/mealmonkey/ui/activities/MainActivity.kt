package com.example.mealmonkey.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.ActivityMainBinding
import com.example.mealmonkey.ui.fragments.mainfragments.HomeFragment
import com.example.mealmonkey.utils.setStatusBarTransparent


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarTransparent(this,binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        binding.bottomNavView.background=null
        binding.bottomNavView.menu[2].isEnabled=false

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment?
        val navController = navHostFragment!!.navController

        binding.bottomNavView.setupWithNavController(navController)

        binding.homeFab.setOnClickListener {
            loadHomeFragment(HomeFragment())
        }

    }

    private fun loadHomeFragment(f: Fragment) {
        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        ft.replace(R.id.fragmentContainerView2, f)
        ft.commit()
    }
}
