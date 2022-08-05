package com.example.mealmonkey.ui.fragments.mainfragments

import android.R
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.fragment.app.Fragment
import com.example.mealmonkey.databinding.FragmentProfileBinding
import com.example.mealmonkey.ui.activities.StartActivity
import com.example.mealmonkey.utils.Constants
import com.example.mealmonkey.utils.PrefsData
import com.example.mealmonkey.utils.trimName
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var _binding:FragmentProfileBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentProfileBinding.inflate(layoutInflater)

        configureGoogleSignIn()

        setUsersData()

        binding.signOutBtn.setOnClickListener {
            logout()
        }

        binding.editProfileBtn.setOnClickListener {

        }
        return binding.root
    }

    private fun setUsersData() {
        val user = PrefsData(requireContext()).getUser()
        binding.nameText.text=user.name
        binding.emailText.text=user.email
        binding.mobileNoText.text=user.phoneNo
        binding.addressText.text=user.address
        binding.helloText.text= "Hi there ${trimName(user.name)}!"

        if(user.photoUrl!="-")
            Picasso.get().load(user.photoUrl).into(binding.profileImage)
        else
            binding.profileImage.setImageResource(com.example.mealmonkey.R.drawable.profile)
    }

    private fun configureGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(requireActivity(),gso)
    }

    private fun logout() {
        when(PrefsData(requireContext()).getLoginType()){
            Constants.CUSTOM_LOGIN -> updateUI()
            Constants.FACEBOOK_LOGIN -> disconnectFromFacebook()
            Constants.GOOGLE_LOGIN -> {
                mGoogleSignInClient.signOut()
                    .addOnCompleteListener(requireActivity(), OnCompleteListener<Void?> {
                        updateUI()
                    })
            }
        }
    }

    private fun updateUI() {
        PrefsData(requireContext()).clearUser()
        startActivity(Intent(requireContext(), StartActivity::class.java))
        requireActivity().finish()
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