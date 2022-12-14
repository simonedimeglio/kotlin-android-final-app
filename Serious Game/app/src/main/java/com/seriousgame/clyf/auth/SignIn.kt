package com.seriousgame.clyf.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.seriousgame.clyf.admin.ViewAdminActivity
import com.seriousgame.clyf.databinding.ActivitySignInBinding


class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding //feature that allows you to more easily write code that interacts with views (replaces findViewById) - F.
    private lateinit var firebaseAuth: FirebaseAuth //define FirebaseAuth - F.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater) //binding - S.
        setContentView(binding.root)    //get XML ActivitySignIn root with binding - F.

        firebaseAuth = FirebaseAuth.getInstance()
        //assign to firebaseAuth a method that returns an instance of this class corresponding to the default FirebaseApp instance - F.

        binding.signinButton.setOnClickListener {   //at signInButton click - F.
            val email = binding.emailET.text.toString() //email field - F.
            val pass = binding.passET.text.toString()   //pass field - F.

            //start if - S.
            if (email.isNotEmpty() && pass.isNotEmpty()){   //check form field - F.
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {    //check user data in Firebase - F.

                        //1st inner if - S.
                        if (it.isSuccessful) { //successful case - S.
                            supportID = email
                            val intent = Intent(this, ViewAdminActivity::class.java) //define intent - S.
                            startActivity(intent) //takes you to Admin activity - S.
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show() //error message - F.
                        }
                        //end 1st inner if - S.
                    }
            } else{
                Toast.makeText(this, "Errore: completa tutti i campi", Toast.LENGTH_LONG).show()    //error message - F.
            }
            //end if - S.
        }

        binding.signinSignup.setOnClickListener {   //if you are not already registered it takes us to SignUp activity - F.
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }


    }
}