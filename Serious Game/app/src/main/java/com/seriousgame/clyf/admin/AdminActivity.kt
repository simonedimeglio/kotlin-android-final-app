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
import com.seriousgame.clyf.auth.supportID
import com.seriousgame.clyf.auth.db
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.popup_create.view.*
import kotlinx.android.synthetic.main.popup_delete.view.*
import kotlinx.android.synthetic.main.popup_modify.view.*
import kotlinx.android.synthetic.main.popup_questions.view.*

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        create.setOnClickListener {
            //popup creation - Y.
            var dialogBuilderCreate : AlertDialog.Builder
            var dialogCreate : AlertDialog?
            var viewCreate = LayoutInflater.from(this).inflate(R.layout.popup_create, null, false)
            dialogBuilderCreate = AlertDialog.Builder(this).setView(viewCreate)
            dialogCreate = dialogBuilderCreate!!.create()
            dialogCreate.show()

            //association of popup elements to variables - Y.
            var quizNameField = viewCreate.editText
            var questionButton = viewCreate.question_menu
            var save1 = viewCreate.save1

            var quiz : MutableMap<String, Any>  //map that will contain the data that the user wants to enter - Y.
            var quizName : MutableMap<String, Any> = hashMapOf()    //map that will contain the name of the quiz - Y.

            var container : ArrayList<MutableMap<String, Any>> = ArrayList()  //array containing all the questions the user wants to enter (array of maps) - Y.

            questionButton.setOnClickListener {
                //popup creation - G.
                var dialogBuilderQuestions : AlertDialog.Builder
                var dialogQuestions : AlertDialog?
                var viewQuestions = LayoutInflater.from(this).inflate(R.layout.popup_questions, null, false)
                dialogBuilderQuestions = AlertDialog.Builder(this).setView(viewQuestions)
                dialogQuestions = dialogBuilderQuestions!!.create()
                dialogQuestions.show()

                //association of popup elements to variables - G.
                var question = viewQuestions.question
                var answer1 = viewQuestions.answer1
                var answer2 = viewQuestions.answer2
                var answer3 = viewQuestions.answer3
                var correctAnswer = viewQuestions.correctanswer
                var save1 = viewQuestions.save2

                save1.setOnClickListener {
                    //check if the fields are not empty - G.
                    if (!TextUtils.isEmpty(question.text) && !TextUtils.isEmpty(answer1.text) && !TextUtils.isEmpty(answer2.text) && !TextUtils.isEmpty(answer3.text) && !TextUtils.isEmpty(correctAnswer.text)){
                        //check if the correct answer is equal to one of the answers - G.
                        if ((correctAnswer.text.toString() == answer1.text.toString()) || ((correctAnswer.text.toString() == answer2.text.toString()) || (correctAnswer.text.toString() == answer3.text.toString()))){

                            //map creation and assignment of values entered by the user - G.
                            quiz = hashMapOf()
                            quiz["Question"] = question.text.toString()
                            quiz["Answer1"] = answer1.text.toString()
                            quiz["Answer2"] = answer2.text.toString()
                            quiz["Answer3"] = answer3.text.toString()
                            quiz["Correct_answer"] = correctAnswer.text.toString()

                            container.add(quiz)   //adding quizzes to container - G.
                            dialogQuestions.dismiss()   //pop-up close - G.
                        }else{
                            Toast.makeText(this, "The correct answer must be equal to one of the answers", Toast.LENGTH_LONG).show()    //error message - G.
                        }
                    }else{
                        Toast.makeText(this, "One of the fields is empty", Toast.LENGTH_LONG).show()    //error message - G.
                    }
                }
            }

            save1.setOnClickListener {
                if (!TextUtils.isEmpty(quizNameField.text)){ //check if the "quiz name" field is not empty - S.
                    quizName["Quiz_name"] = quizNameField.text.toString()    //database field creation - S.
                    db.collection(supportID).document().set(quizName)    //add quiz name to database - S.
                        .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }

                    for (i in 0 until container.size){
                        var support = container.get(i)  //we take the i-th element from the container and insert it into support - S.

                        db.collection(supportID).document().set(support)    //inserting the i-th element into the database - S.
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
                    }

                    dialogCreate.dismiss()  //pop-up close - S.
                }else{
                    Toast.makeText(this, "Quiz name field empty", Toast.LENGTH_LONG).show() //error message - S.
                }
            }
        }

        add.setOnClickListener {
            //popup creation - F.
            var dialogBuilderAdd : AlertDialog.Builder
            var dialogAdd : AlertDialog?
            var viewAdd = LayoutInflater.from(this).inflate(R.layout.popup_questions, null, false)
            dialogBuilderAdd = AlertDialog.Builder(this).setView(viewAdd)
            dialogAdd = dialogBuilderAdd!!.create()
            dialogAdd.show()

            //association of popup elements to variables - F.
            var question = viewAdd.question
            var answer1 = viewAdd.answer1
            var answer2 = viewAdd.answer2
            var answer3 = viewAdd.answer3
            var correctAnswer = viewAdd.correctanswer
            var save = viewAdd.save2

            var quiz : MutableMap<String, Any>  //map that will contain the data that the user wants to enter - F.

            save.setOnClickListener {
                //check if the fields are not empty - F.
                if (!TextUtils.isEmpty(question.text) && !TextUtils.isEmpty(answer1.text) && !TextUtils.isEmpty(answer2.text) && !TextUtils.isEmpty(answer3.text) && !TextUtils.isEmpty(correctAnswer.text)){
                    //check if the correct answer is equal to one of the answers - F.
                    if ((correctAnswer.text.toString() == answer1.text.toString()) || ((correctAnswer.text.toString() == answer2.text.toString()) || (correctAnswer.text.toString() == answer3.text.toString()))){

                        //map creation and assignment of values entered by the user - F.
                        quiz = hashMapOf()
                        quiz["Question"] = question.text.toString()
                        quiz["Answer1"] = answer1.text.toString()
                        quiz["Answer2"] = answer2.text.toString()
                        quiz["Answer3"] = answer3.text.toString()
                        quiz["Correct_answer"] = correctAnswer.text.toString()

                        db.collection(supportID).document().set(quiz)   //entering data into the database - F.
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }

                        dialogAdd.dismiss() //pop-up close - F.

                    }else{
                        Toast.makeText(this, "The correct answer must be equal to one of the answers", Toast.LENGTH_LONG).show()    //error message - F.
                    }
                }else{
                    Toast.makeText(this, "One of the fields is empty", Toast.LENGTH_LONG).show()    //error message - F.
                }
            }

        }

        modify.setOnClickListener {

            //creation of support variables - Y.
            var questionList : ArrayList<String> = ArrayList()
            var questionToUpdate : String? = null

            //popup creation - Y.
            var dialogBuilderModify : AlertDialog.Builder
            var dialogModify : AlertDialog?
            var viewModify = LayoutInflater.from(this).inflate(R.layout.popup_modify, null, false)
            dialogBuilderModify = AlertDialog.Builder(this).setView(viewModify)
            dialogModify = dialogBuilderModify!!.create()
            dialogModify.show()

            var modifyButton = viewModify.modify1

            //query the database and take all the documents with the component question not null - Y.
            db.collection(supportID).whereNotEqualTo("Question", null).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        questionList.add(document.data["Question"].toString())   //insert the data contained in the question field into the array - Y.
                    }

                    //creating the spinner and inserting data into it - Y.
                    var spinner : Spinner = viewModify.spinner
                    var adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, questionList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            questionToUpdate = questionList[position]   //insertion of the question chosen by the user in the support variable - Y.
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(applicationContext, "Nothing selected", Toast.LENGTH_LONG).show()    //error message - Y.
                        }
                    }

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, "You need to insert questions first!", Toast.LENGTH_LONG).show()
                    dialogModify.dismiss()
                }

            modifyButton.setOnClickListener {
                if (questionToUpdate != null){
                    //popup creation - G.
                    var dialogBuilderQuestion : AlertDialog.Builder
                    var dialogQuestion : AlertDialog?
                    var viewQuestion = LayoutInflater.from(applicationContext).inflate(R.layout.popup_questions, null, false)
                    dialogBuilderQuestion = AlertDialog.Builder(this).setView(viewQuestion)
                    dialogQuestion = dialogBuilderQuestion!!.create()
                    dialogQuestion.show()

                    //association of popup elements to variables - G.
                    var question = viewQuestion.question
                    var answer1 = viewQuestion.answer1
                    var answer2 = viewQuestion.answer2
                    var answer3 = viewQuestion.answer3
                    var correctAnswer = viewQuestion.correctanswer
                    var save = viewQuestion.save2

                    var quiz : MutableMap<String, Any>  //map that will contain the data that the user wants to enter - G.

                    save.setOnClickListener {
                        //check if the fields are not empty - G.
                        if (!TextUtils.isEmpty(question.text) && !TextUtils.isEmpty(answer1.text) && !TextUtils.isEmpty(answer2.text) && !TextUtils.isEmpty(answer3.text) && !TextUtils.isEmpty(correctAnswer.text)){
                            //check if the correct answer is equal to one of the answers - G.
                            if ((correctAnswer.text.toString() == answer1.text.toString()) || ((correctAnswer.text.toString() == answer2.text.toString()) || (correctAnswer.text.toString() == answer3.text.toString()))){

                                //map creation and assignment of values entered by the user - G.
                                quiz = hashMapOf()
                                quiz["Question"] = question.text.toString()
                                quiz["Answer1"] = answer1.text.toString()
                                quiz["Answer2"] = answer2.text.toString()
                                quiz["Answer3"] = answer3.text.toString()
                                quiz["Correct_answer"] = correctAnswer.text.toString()

                                //query the database and take all the documents with the component question equals to questionToUpdate - G.
                                db.collection(supportID)
                                    .whereEqualTo("Question", questionToUpdate)
                                    .get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            var id = document.id    //we take the document ID and put it into a support variable - G.
                                            db.collection(supportID).document(id).update(quiz)  //update - G.
                                        }
                                    }

                                dialogQuestion.dismiss()    //pop-up close - G.
                                dialogModify.dismiss()  //pop-up close - G.
                            }else{
                                Toast.makeText(this, "The correct answer must be equal to one of the answers", Toast.LENGTH_LONG).show()    //error message - G.
                            }
                        }else{
                            Toast.makeText(this, "One of the fields is empty", Toast.LENGTH_LONG).show()    //error message - G.
                        }
                    }

                }else{
                    Toast.makeText(this, "You need to select one question first!", Toast.LENGTH_LONG).show()    //error message - G.
                }
            }

        }

        delete.setOnClickListener {

            //creation of support variables - S.
            var questionList : ArrayList<String> = ArrayList()
            var questionToDelete : String? = null

            //popup creation - S.
            var dialogBuilderDelete : AlertDialog.Builder
            var dialogDelete : AlertDialog?
            var viewDelete = LayoutInflater.from(this).inflate(R.layout.popup_delete, null, false)
            dialogBuilderDelete = AlertDialog.Builder(this).setView(viewDelete)
            dialogDelete = dialogBuilderDelete!!.create()
            dialogDelete.show()

            var deleteButton = viewDelete.delete1

            //query the database and take all the documents with the component question not null - S.
            db.collection(supportID).whereNotEqualTo("Question", null)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        questionList.add(document.data["Question"].toString())  //insert the data contained in the question field into the array - S.
                    }

                    //creating the spinner and inserting data into it - S.
                    var spinner : Spinner = viewDelete.spinner_delete
                    var adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, questionList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            questionToDelete = questionList[position]   //insertion of the question chosen by the user in the support variable - S.
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            Toast.makeText(applicationContext, "Nothing selected", Toast.LENGTH_LONG).show()
                        }
                    }

                }

            deleteButton.setOnClickListener {
                if (questionToDelete != null){
                    //query the database and take all the documents with the component question equals to questionToDelete - S.
                    db.collection(supportID)
                        .whereEqualTo("Question", questionToDelete)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                var id = document.id    //we take the document ID and put it into a support variable - S.
                                db.collection(supportID).document(id).delete()  //delete - S.
                            }
                        }

                    dialogDelete.dismiss()  //pop-up close - S.
                }
            }

        }

        exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)   //return to the MainActivity - Y.
        }

    }

}