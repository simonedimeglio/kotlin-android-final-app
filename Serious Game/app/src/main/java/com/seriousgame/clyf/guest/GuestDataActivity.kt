package com.seriousgame.clyf.guest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.Query
import com.seriousgame.clyf.MainActivity
import com.seriousgame.clyf.R
import com.seriousgame.clyf.admin.ViewAdminActivity
import com.seriousgame.clyf.auth.db
import com.seriousgame.clyf.auth.nicknameID
import com.seriousgame.clyf.auth.scores
import com.seriousgame.clyf.auth.supportID
import kotlinx.android.synthetic.main.activity_guest_data.*
import kotlinx.android.synthetic.main.popup_password.*
import kotlinx.android.synthetic.main.popup_password.view.*

class GuestDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_data)

        val startButton = guestSignInButton
        val leaderboardButton = guestLeaderboardButton

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

        leaderboardButton.setOnClickListener {
            var dialogBuilderLead : AlertDialog.Builder
            var dialogLead : AlertDialog?
            var viewLead = LayoutInflater.from(this).inflate(R.layout.popup_password, null, false)
            dialogBuilderLead = AlertDialog.Builder(this).setView(viewLead)
            dialogLead = dialogBuilderLead!!.create()
            dialogLead.show()

            val nextButton = viewLead.nextLeaderboard

            nextButton.setOnClickListener {

                var support = viewLead.passwordLeaderboard
                var password = support.text.toString()
                if (!TextUtils.isEmpty(password)){
                    var supportPass = ""
                    db.collection("quizPasswords").whereEqualTo("quizPassword", password).get()
                        .addOnSuccessListener { result ->
                            for (document in result){
                                supportID = document.data["ID"].toString()
                                supportPass = password
                                db.collection("scores").whereEqualTo("ID", supportID).orderBy("Score", Query.Direction.DESCENDING).get()
                                    .addOnSuccessListener { result2 ->
                                        scores.clear()
                                        for (document in result2){
                                            var arraySupport : ArrayList<String> = ArrayList()
                                            arraySupport.add(document.data["Nickname"].toString())
                                            arraySupport.add(document.data["Score"].toString())
                                            scores.add(arraySupport)
                                        }
                                        val intent = Intent(this, LeaderboardActivity::class.java)
                                        startActivity(intent)
                                    }
                            }
                            if (supportPass != password){
                                Toast.makeText(this, "quizPassword does not exist", Toast.LENGTH_LONG).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "One of the fields is empty", Toast.LENGTH_LONG).show()
                }

            }

        }


    }
}