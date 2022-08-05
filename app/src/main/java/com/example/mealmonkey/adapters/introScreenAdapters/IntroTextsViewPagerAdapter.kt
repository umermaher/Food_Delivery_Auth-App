package com.example.mealmonkey.adapters.introScreenAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.mealmonkey.R
import com.example.mealmonkey.models.IntroScreenItem

class IntroTextsViewPagerAdapter(private val context: Context, private val itemList:List<IntroScreenItem>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //screen layout is actually a pager layout
        val screenLayout=layoutInflater.inflate(R.layout.intro_text_layout,null)
        val item= itemList[position]

        val titleText=screenLayout.findViewById<TextView>(R.id.introTitle)
        val introText=screenLayout.findViewById<TextView>(R.id.introText)

        titleText.text=item.title
        introText.text=item.description
        container.addView(screenLayout)

        return screenLayout
    }

    override fun getCount(): Int = itemList.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view==`object`

}