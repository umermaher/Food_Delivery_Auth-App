package com.example.mealmonkey.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mealmonkey.R
import com.example.mealmonkey.databinding.FragmentLoginBinding
import com.example.mealmonkey.ui.activities.MainActivity
import com.example.mealmonkey.ui.activities.SignUpActivity
import com.example.mealmonkey.utils.TextValidations.validateEmail
import com.example.mealmonkey.utils.TextValidations.validatePassword
import com.facebook.*
import com.facebook.CallbackManager.Factory.create
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager:CallbackManager

    companion object {
        private const val RC_GOOGLE_SIGN_IN=99
        const val TAG="LoginFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentLoginBinding.inflate(layoutInflater)

        //configuring googleSignIn
        configureGoogleSignIn()
        configureFacebookSignIn()

        binding.loginBtn.setOnClickListener {
            customSignIn()
        }

        binding.fbSignInBtn.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile", "user_friends"));
        }

        binding.googleSignInBtn.setOnClickListener{
            googleSignIn()
        }

        binding.signUpText.setOnClickListener {
            val intent=Intent(requireContext(),SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        return binding.root
    }

    private fun configureFacebookSignIn() {
        callbackManager = create()

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                Toast.makeText(requireContext(),"Login cancelled!",Toast.LENGTH_LONG).show()
            }
            override fun onError(error: FacebookException) {
                    // App code
                Toast.makeText(requireContext(),"Login failed: "+error.message,Toast.LENGTH_LONG).show()
            }
            override fun onSuccess(result: LoginResult) {
//                val graphRequest=GraphRequest.newMeRequest(result.accessToken){userObj,response ->
////                    Toast.makeText(requireContext(),response?.error?.errorMessage,Toast.LENGTH_LONG).show()
//                    getFbUserData(userObj!!)
//                }
                var email:String
                var profilePic:String
                var name:String

                val graphRequest=GraphRequest.newMeRequest(result.accessToken) { obj, response ->
                    if (obj != null) {
                        getFbUserData(obj)
                    }
                }
                val parameters=Bundle()
                parameters.putString("fields","email,id,name")
                graphRequest.parameters=parameters
                graphRequest.executeAsync()
                Toast.makeText(requireContext(),"Logged In!",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getFbUserData(userObj: JSONObject) {
        try {
            val profilePic="https://graph.facebook.com/${userObj.getString("id")}/picture?width=200&height=200"
            val name=userObj.getString("name")
            val email=userObj.getString("email")
            val intent=Intent(requireContext(),MainActivity::class.java)
            intent.putExtra("name",profilePic)
            startActivity(intent)
        } catch (e: JSONException) {
            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun configureGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(requireActivity(),gso)
    }

    private fun customSignIn() {
        val test1= validateEmail(binding.emailEditText)
        val test2= validatePassword(binding.passwordEditText)
        when(false){
            test1 -> return
            test2 -> return
            else -> {}
        }
        Toast.makeText(requireContext(),"hell",Toast.LENGTH_SHORT).show()
    }

    private fun googleSignIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            RC_GOOGLE_SIGN_IN -> {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleGoogleSignInResult(task)
            }
        }
    }

    private fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            binding.loginPb.visibility = View.VISIBLE
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if(account!=null){
            val intent=Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("name",account.email)
            binding.loginPb.visibility=View.GONE
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding=null
    }
}