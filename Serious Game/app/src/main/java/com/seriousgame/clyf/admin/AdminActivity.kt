package com.seriousgame.clyf.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.seriousgame.clyf.MainActivity
import com.seriousgame.clyf.R
import com.seriousgame.clyf.auth.appoggioID
import com.seriousgame.clyf.auth.db
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.popup_create.*
import kotlinx.android.synthetic.main.popup_create.view.*
import kotlinx.android.synthetic.main.popup_delete.view.*
import kotlinx.android.synthetic.main.popup_modify.*
import kotlinx.android.synthetic.main.popup_modify.view.*
import kotlinx.android.synthetic.main.popup_questions.view.*

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
                var view1 = LayoutInflater.from(this).inflate(R.layout.popup_questions, null, false)
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

        add.setOnClickListener {

            var dialogBuilderAdd : AlertDialog.Builder
            var dialogAdd : AlertDialog?
            var viewAdd = LayoutInflater.from(this).inflate(R.layout.popup_questions, null, false)
            var question = viewAdd.question
            var answer1 = viewAdd.answer1
            var answer2 = viewAdd.answer2
            var answer3 = viewAdd.answer3
            var correctAnswer = viewAdd.correctanswer
            var save = viewAdd.save2
            var quiz : MutableMap<String, Any>

            dialogBuilderAdd = AlertDialog.Builder(this).setView(viewAdd)
            dialogAdd = dialogBuilderAdd!!.create()
            dialogAdd.show()

            save.setOnClickListener {
                if (!TextUtils.isEmpty(question.text) && !TextUtils.isEmpty(answer1.text) && !TextUtils.isEmpty(answer2.text) && !TextUtils.isEmpty(answer3.text) && !TextUtils.isEmpty(correctAnswer.text)){
                    if ((correctAnswer.text.toString() == answer1.text.toString()) || ((correctAnswer.text.toString() == answer2.text.toString()) || (correctAnswer.text.toString() == answer3.text.toString()))){

                        quiz = hashMapOf()
                        quiz["Domanda"] = question.text.toString()
                        quiz["Risposta1"] = answer1.text.toString()
                        quiz["Risposta2"] = answer2.text.toString()
                        quiz["Risposta3"] = answer3.text.toString()
                        quiz["Risposta_Corretta"] = correctAnswer.text.toString()

                        db.collection(appoggioID).document().set(quiz)
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }

                        dialogAdd.dismiss()

                    }else{
                        Toast.makeText(this, "La risposta corretta deve essere uguale a una delle risposte", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, "Non hai inserito un campo", Toast.LENGTH_LONG).show()
                }
            }



        }

        modify.setOnClickListener {

            var questionList : ArrayList<String> = ArrayList()
            var questionToUpdate : String? = null

            var dialogBuilder3 : AlertDialog.Builder
            var dialog3 : AlertDialog?
            var view3 = LayoutInflater.from(this).inflate(R.layout.popup_modify, null, false)
            var modifyButton = view3.modify1

            dialogBuilder3 = AlertDialog.Builder(this).setView(view3)
            dialog3 = dialogBuilder3!!.create()
            dialog3.show()

            db.collection(appoggioID).whereNotEqualTo("Domanda", null)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        questionList.add(document.data["Domanda"].toString())
                    }

                    var spinner : Spinner = view3.spinner
                    var adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, questionList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            questionToUpdate = questionList[position]
                            Toast.makeText(applicationContext, "The selected question is " + questionList[position], Toast.LENGTH_LONG).show()
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(applicationContext, "Nothing selected", Toast.LENGTH_LONG).show()
                        }
                    }

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, "You need to insert questions first!", Toast.LENGTH_LONG).show()
                    dialog3.dismiss()
                }

            modifyButton.setOnClickListener {
                if (questionToUpdate != null){
                    var dialogBuilder4 : AlertDialog.Builder
                    var dialog4 : AlertDialog?
                    var view4 = LayoutInflater.from(applicationContext).inflate(R.layout.popup_questions, null, false)
                    var question = view4.question
                    var answer1 = view4.answer1
                    var answer2 = view4.answer2
                    var answer3 = view4.answer3
                    var correctAnswer = view4.correctanswer
                    var save = view4.save2
                    var quiz : MutableMap<String, Any>

                    dialogBuilder4 = AlertDialog.Builder(this).setView(view4)
                    dialog4 = dialogBuilder4!!.create()
                    dialog4.show()

                    save.setOnClickListener {
                        if (!TextUtils.isEmpty(question.text) && !TextUtils.isEmpty(answer1.text) && !TextUtils.isEmpty(answer2.text) && !TextUtils.isEmpty(answer3.text) && !TextUtils.isEmpty(correctAnswer.text)){
                            if ((correctAnswer.text.toString() == answer1.text.toString()) || ((correctAnswer.text.toString() == answer2.text.toString()) || (correctAnswer.text.toString() == answer3.text.toString()))){
                                quiz = hashMapOf()
                                quiz["Domanda"] = question.text.toString()
                                quiz["Risposta1"] = answer1.text.toString()
                                quiz["Risposta2"] = answer2.text.toString()
                                quiz["Risposta3"] = answer3.text.toString()
                                quiz["Risposta_Corretta"] = correctAnswer.text.toString()

                                db.collection(appoggioID)
                                    .whereEqualTo("Domanda", questionToUpdate)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            var id = document.id
                                            db.collection(appoggioID).document(id).update(quiz)
                                        }
                                    }

                                dialog4.dismiss()
                                dialog3.dismiss()
                            }else{
                                Toast.makeText(this, "La risposta corretta deve essere uguale a una delle risposte", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Toast.makeText(this, "Non hai inserito un campo", Toast.LENGTH_LONG).show()
                        }
                    }

                }else{
                    Toast.makeText(this, "You need to select one question first!", Toast.LENGTH_LONG).show()
                }
            }


        }

        delete.setOnClickListener {

            var questionList : ArrayList<String> = ArrayList()
            var questionToDelete : String? = null

            var dialogBuilderDelete : AlertDialog.Builder
            var dialogDelete : AlertDialog?
            var viewDelete = LayoutInflater.from(this).inflate(R.layout.popup_delete, null, false)
            var deleteButton = viewDelete.delete1

            dialogBuilderDelete = AlertDialog.Builder(this).setView(viewDelete)
            dialogDelete = dialogBuilderDelete!!.create()
            dialogDelete.show()

            db.collection(appoggioID).whereNotEqualTo("Domanda", null)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        questionList.add(document.data["Domanda"].toString())
                    }

                    var spinner : Spinner = viewDelete.spinner_delete
                    var adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, questionList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            questionToDelete = questionList[position]
                            Toast.makeText(applicationContext, "The selected question is " + questionList[position], Toast.LENGTH_LONG).show()
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(applicationContext, "Nothing selected", Toast.LENGTH_LONG).show()
                        }
                    }

                }

            deleteButton.setOnClickListener {
                if (questionToDelete != null){

                    db.collection(appoggioID)
                        .whereEqualTo("Domanda", questionToDelete)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                var id = document.id
                                db.collection(appoggioID).document(id).delete()
                            }
                        }

                    dialogDelete.dismiss()

                }
            }

        }

        exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }











    }





}