package com.seriousgame.clyf.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.seriousgame.clyf.MainActivity
import com.seriousgame.clyf.R
import com.seriousgame.clyf.databinding.ActivitySignInBinding
import com.seriousgame.clyf.databinding.ActivitySignUpBinding

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding //feature that allows you to more easily write code that interacts with views (replaces findViewById) - F.
    private lateinit var firebaseAuth: FirebaseAuth //define FirebaseAuth - F.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)    //get XML ActivitySignIn root with binding - F.

        firebaseAuth = FirebaseAuth.getInstance()
        //assign to firebaseAuth a method that returns an instance of this class corresponding to the default FirebaseApp instance - F.

        binding.signinButton.setOnClickListener {   //at signInButton click - F.
            val email = binding.emailET.text.toString() //email field - F.
            val pass = binding.passET.text.toString()   //pass field - F.

            if (email.isNotEmpty() && pass.isNotEmpty()){   //check form field - F.
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {    //check user data in Firebase - F.
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show() //error message - F.
                        }
                    }
            } else{
                Toast.makeText(this, "Errore: completa tutti i campi", Toast.LENGTH_LONG).show()    //error message - F.
            }

        }

        binding.signinSignup.setOnClickListener {   //if you are not already registered it takes us to SignUp activity - F.
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}