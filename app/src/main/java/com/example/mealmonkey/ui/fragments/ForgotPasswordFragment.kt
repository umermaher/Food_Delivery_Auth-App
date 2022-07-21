package com.example.mealmonkey.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentForgotPasswordBinding
import com.example.mealmonkey.databinding.FragmentLoginBinding


class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentForgotPasswordBinding.inflate(layoutInflater)

        return binding.root
    }
}