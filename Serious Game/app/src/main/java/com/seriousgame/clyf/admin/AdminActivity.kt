package com.seriousgame.clyf.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.SetOptions
import com.seriousgame.clyf.R
import com.seriousgame.clyf.auth.appoggioID
import com.seriousgame.clyf.auth.db
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.popup_create.*
import kotlinx.android.synthetic.main.popup_create.view.*
import kotlinx.android.synthetic.main.popup_domande.view.*
import kotlinx.android.synthetic.main.popup_modify.*

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        create.setOnClickListener {
            var dialogBuilder : AlertDialog.Builder
            var dialog : AlertDialog?
            var view = LayoutInflater.from(this).inflate(R.layout.popup_create, null, false)
            var nomeQuiz = view.editText
            var questionButton = view.question_menu
            var save1 = view.save1
            var quiz : MutableMap<String, Any>
            var quizName : MutableMap<String, Any> = hashMapOf()

            var contenitore : ArrayList<MutableMap<String, Any>> = ArrayList()
            var contatore : Int = 0

            dialogBuilder = AlertDialog.Builder(this).setView(view)
            dialog = dialogBuilder!!.create()
            dialog.show()

            questionButton.setOnClickListener {
                var dialogBuilder1 : AlertDialog.Builder
                var dialog1 : AlertDialog?
                var view1 = LayoutInflater.from(this).inflate(R.layout.popup_domande, null, false)
                var question = view1.question
                var answer1 = view1.answer1
                var answer2 = view1.answer2
                var answer3 = view1.answer3
                var correctAnswer = view1.correctanswer
                var save1 = view1.save2

                dialogBuilder1 = AlertDialog.Builder(this).setView(view1)
                dialog1 = dialogBuilder1!!.create()
                dialog1.show()

                save1.setOnClickListener {
                    if (!TextUtils.isEmpty(question.text) && !TextUtils.isEmpty(answer1.text) && !TextUtils.isEmpty(answer2.text) && !TextUtils.isEmpty(answer3.text) && !TextUtils.isEmpty(correctAnswer.text)){
                        if ((correctAnswer.text.toString() == answer1.text.toString()) || ((correctAnswer.text.toString() == answer2.text.toString()) || (correctAnswer.text.toString() == answer3.text.toString()))){
                            quiz = hashMapOf()
                            quiz["Domanda"] = question.text.toString()
                            quiz["Risposta1"] = answer1.text.toString()
                            quiz["Risposta2"] = answer2.text.toString()
                            quiz["Risposta3"] = answer3.text.toString()
                            quiz["Risposta_Corretta"] = correctAnswer.text.toString()

                            contenitore.add(quiz)
                            dialog1.dismiss()
                        }else{
                            Toast.makeText(this, "La risposta corretta deve essere uguale a una delle risposte", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(this, "Non hai inserito un campo", Toast.LENGTH_LONG).show()
                    }
                }
            }

            save1.setOnClickListener {
                if (!TextUtils.isEmpty(nomeQuiz.text)){
                    quizName["Nome_quiz"] = nomeQuiz.text.toString()
                    db.collection(appoggioID).document("quizName").set(quizName)
                        .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }

                    for (i in 0 until contenitore.size){
                        var appoggio = contenitore.get(i)
                        contatore += 1

                        db.collection(appoggioID).document("Question${contatore}").set(appoggio)
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                    }

                    dialog.dismiss()
                }else{
                    Toast.makeText(this, "Non hai inserito il nome del Quiz", Toast.LENGTH_LONG).show()
                }
            }
        }

        modify.setOnClickListener {
            var dialogBuilder3 : AlertDialog.Builder
            var dialog3 : AlertDialog?
            var view3 = LayoutInflater.from(this).inflate(R.layout.popup_modify, null, false)

            dialogBuilder3 = AlertDialog.Builder(this).setView(view3)
            dialog3 = dialogBuilder3!!.create()
            dialog3.show()

        }









    }





}