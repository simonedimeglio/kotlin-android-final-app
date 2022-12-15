package com.seriousgame.clyf.auth
import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

var supportID : String = ""
var nicknameID : String = ""
@SuppressLint("StaticFieldLeak")
var db = FirebaseFirestore.getInstance()

class quizBlock {
    var question : String = ""
    var answer1 : String = ""
    var answer2 : String = ""
    var answer3 : String = ""
    var correct_answer : String = ""
}