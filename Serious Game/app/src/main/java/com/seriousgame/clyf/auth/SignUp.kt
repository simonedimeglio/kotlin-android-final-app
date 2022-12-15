package com.seriousgame.clyf.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.seriousgame.clyf.R
import com.seriousgame.clyf.databinding.ActivityMainBinding
import com.seriousgame.clyf.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding //feature that allows you to more easily write code that interacts with views (replaces findViewById) - F.
    private lateinit var firebaseAuth: FirebaseAuth //define FirebaseAuth - F.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater) //binding - S.
        setContentView(binding.root)    //get XML ActivitySignUp root with binding - F.

        firebaseAuth = FirebaseAuth.getInstance()
        //assign to firebaseAuth a method that returns an instance of this class corresponding to the default FirebaseApp instance - F.
        //db = FirebaseFirestore.getInstance()    // Access a Cloud Firestore instance from your Activity -F.

        binding.signupButton.setOnClickListener {   //at signupButton click - F.
            val email = binding.emailET.text.toString() //email field - F.
            val pass = binding.passET.text.toString()   //pass field - F.
            val confirmpass = binding.confirmPassEt.text.toString() //confirm_pass field - F.

            //start if - S.
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()){   //check form field - F.
                //1st inner if - S.
                if (pass == confirmpass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {    //add new user in Firebase with email and pass - F.
                        //FirebaseAuth: password must have almost six char - S.

                        //2nd inner if - S.
                        if (it.isSuccessful){ //succesful case - S.
                            val intent = Intent(this, SignIn::class.java) //define intent - S.
                            startActivity(intent)   //takes you to SignIn activity - F.
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show() //error message - F.
                        }
                        //end 2nd inner if - S.
                    }
                }else{
                    Toast.makeText(this, "Le password non sono coincidenti", Toast.LENGTH_LONG).show()  //error message - F.
                }
                //end 1st inner if - S.
            }else{
                Toast.makeText(this, "Errore: completa tutti i campi", Toast.LENGTH_LONG).show()    //error message - F.
            }
            //end if - S.
        }

        binding.signupSignin.setOnClickListener {   //if you have already registered it takes us to SignIn activity - F.
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}