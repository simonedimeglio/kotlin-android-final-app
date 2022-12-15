package com.seriousgame.clyf.guest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.seriousgame.clyf.R
import com.seriousgame.clyf.admin.ViewAdminActivity
import com.seriousgame.clyf.auth.db
import com.seriousgame.clyf.auth.nicknameID
import com.seriousgame.clyf.auth.supportID
import kotlinx.android.synthetic.main.activity_guest_data.*

class GuestDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_data)

        val startButton = guestSignInButton

        startButton.setOnClickListener {

            var nickname = NicknameET.text.toString()
            var quizPassword = QuizPassET.text.toString()

            if (!TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(quizPassword)){
                var supportPass = ""
                db.collection("quizPasswords").whereEqualTo("quizPassword", quizPassword).get()
                    .addOnSuccessListener { result ->
                        for (document in result){
                            supportID = document.data["ID"].toString()
                            nicknameID = nickname
                            supportPass = quizPassword
                            val intent = Intent(this, ViewGuestActivity::class.java)
                            startActivity(intent)
                        }
                        if (supportPass != quizPassword){
                            Toast.makeText(this, "quizPassword does not exist", Toast.LENGTH_LONG).show()
                        }

                    }
            }else{
                Toast.makeText(this, "One of the fields is empty", Toast.LENGTH_LONG).show()
            }

        }

    }
}