package com.example.coffeedairy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeedairy.MyAuth.Companion.auth
import com.example.coffeedairy.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity: AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            //구글 로그인 결과 처리...........................
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                MyAuth.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            //MyAuth.email = account.email
                            Toast.makeText(this, "환영합니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            //changeVisibility("login")
                        }else {
                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()

                            //changeVisibility("logout")
                        }
                    }
            }catch (e: ApiException){
                //changeVisibility("logout")
            }
        }

        binding.authGoogleSignInButton.setOnClickListener {
            //구글 로그인....................
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("218058031453-d92sn3ln73g8t1qkmme5juu98rmi9pta.apps.googleusercontent.com")
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }
    }
//
//    fun changeVisibility(mode: String){
//        when(mode){
//            "login" -> {
//                binding.authGoogleSignInButton.visibility = View.GONE
//            }
//            "logout" -> {
//                binding.authGoogleSignInButton.visibility = View.VISIBLE
//            }
//            "signin" -> {
//                binding.authGoogleSignInButton.visibility = View.GONE
//            }
//        }
//    }

}