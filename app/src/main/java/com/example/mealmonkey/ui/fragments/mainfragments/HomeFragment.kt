package com.example.mealmonkey.ui.fragments.mainfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmonkey.databinding.FragmentHomeBinding
import com.example.mealmonkey.utils.PrefsData
import com.example.mealmonkey.utils.trimName
import java.util.*


class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentHomeBinding.inflate(layoutInflater)

        setWishesText()

        return binding.root
    }

    private fun setWishesText() {
        val c= Calendar.getInstance()
        val name=trimName(PrefsData(requireContext()).getUser().name)
        if(c.get(Calendar.HOUR_OF_DAY) in 5..11)
            binding.wishText.text="Good morning $name!"
        else if(c.get(Calendar.HOUR_OF_DAY) in 12..17)
            binding.wishText.text="Good Afternoon $name!"
        else if(c.get(Calendar.HOUR_OF_DAY) in 18..21)
            binding.wishText.text="Good Evening $name!"
        else if(c.get(Calendar.HOUR_OF_DAY) in 22..24 && c.get(Calendar.HOUR_OF_DAY) in 1..4)
            binding.wishText.text="Good Night $name!"
    }
}