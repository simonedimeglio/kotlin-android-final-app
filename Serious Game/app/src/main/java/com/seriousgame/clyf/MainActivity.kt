package com.seriousgame.clyf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seriousgame.clyf.auth.SignIn
import com.seriousgame.clyf.guest.GuestDataActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        creator.setOnClickListener {
            val intent = Intent(this, SignIn::class.java) //define intent - F.
            startActivity(intent) //takes you to SignIn activity - F.
        }

        guest.setOnClickListener {
            val intent = Intent(this, GuestDataActivity::class.java) //define intent - F.
            startActivity(intent) //takes you to SignIn activity - F.
        }



    }

}