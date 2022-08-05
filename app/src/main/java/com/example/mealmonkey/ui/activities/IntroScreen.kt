package com.example.mealmonkey.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.mealmonkey.R
import com.example.mealmonkey.adapters.introScreenAdapters.IntroImageViewPagerAdapter
import com.example.mealmonkey.adapters.introScreenAdapters.IntroTextsViewPagerAdapter
import com.example.mealmonkey.databinding.ActivityIntroScreenBinding
import com.example.mealmonkey.models.IntroScreenItem
import com.example.mealmonkey.utils.PrefsData
import com.example.mealmonkey.utils.setStatusBarTransparent

class IntroScreen : AppCompatActivity() {
    private val list = mutableListOf<IntroScreenItem>()
    private lateinit var binding:ActivityIntroScreenBinding
    private lateinit var introImageViewAdapter: IntroImageViewPagerAdapter
    private lateinit var introTextViewAdapter: IntroTextsViewPagerAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIntroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarTransparent(this,binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        list.add(IntroScreenItem(R.drawable.ic_carousel_1,"Find What You Love",getString(R.string.introFirstText)))
        list.add(IntroScreenItem(R.drawable.ic_carousel_2,"Fast Delivery",getString(R.string.introSecondText)))
        list.add(IntroScreenItem(R.drawable.ic_carousel_3,"Play more games",getString(R.string.introThirdText)))

        introImageViewAdapter=IntroImageViewPagerAdapter(this,list)
        introTextViewAdapter= IntroTextsViewPagerAdapter(this,list)

        binding.introImagePager.adapter=introImageViewAdapter
        binding.introTextPagers.adapter=introTextViewAdapter

        binding.dotsIndicator.attachTo(binding.introImagePager)

        binding.introImagePager.setOnTouchListener { _, event ->
            binding.introTextPagers.onTouchEvent(event)
            false
        }

        binding.introTextPagers.setOnTouchListener { _, event ->
            binding.introImagePager.onTouchEvent(event)
            false
        }

        binding.nextBtn.setOnClickListener {
            var position=binding.introImagePager.currentItem
            if(position<list.size){
                position++
                binding.introTextPagers.currentItem=position
                binding.introImagePager.currentItem=position
                if(position==list.size){
                    //when we reach to the last intro page
                    // TODO: Let's go to the main Activity
                    loadLastScreen()
                }
            }
        }
    }

    private fun loadLastScreen() {
        PrefsData(this).yesFirstTime()
        startActivity(Intent(this,StartActivity::class.java))
    }
}