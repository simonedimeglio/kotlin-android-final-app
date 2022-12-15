package com.seriousgame.clyf.guest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seriousgame.clyf.R
import com.seriousgame.clyf.admin.QuizAdapter
import com.seriousgame.clyf.auth.db
import com.seriousgame.clyf.auth.supportID
import kotlinx.android.synthetic.main.activity_view_guest.*

class ViewGuestActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var contenitore: ArrayList<ArrayList<String>>
    lateinit var adapter: GuestQuizAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    private fun refreshDatabase(x: ArrayList<String>) {
        if (contenitore.size == 0){
            contenitore.add(x)
            adapter.notifyDataSetChanged()
        }
        else{
            var contatore = 0
            for (i in 0 until contenitore.size){
                contatore += 1
                var confrontoSupport = contenitore.get(i)

                if (x.get(0) == confrontoSupport.get(0)){
                    break
                }
                else if (contatore != contenitore.size){
                    continue
                }
                else{
                    contenitore.add(x)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_guest)

        var title = titleVT
        var save = guestSave

        db.collection(supportID).whereNotEqualTo("Quiz_name", null).get()
            .addOnSuccessListener { result ->
                for (document in result){
                    var supportTitle = document.data["Quiz_name"].toString()
                    title.text = "Welcome to ${supportTitle} Quiz "
                }
            }

        recyclerView = findViewById(R.id.recycleViewIdGuest)
        contenitore = ArrayList()
        layoutManager = LinearLayoutManager(this)

        adapter = GuestQuizAdapter(contenitore, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        db.collection(supportID).whereNotEqualTo("Question", null).get()
            .addOnSuccessListener { result ->
                for (document in result){
                    var question = document.data["Question"].toString()
                    var answer1 = document.data["Answer1"].toString()
                    var answer2 = document.data["Answer2"].toString()
                    var answer3 = document.data["Answer3"].toString()
                    var correctAnswer = document.data["Correct_answer"].toString()

                    var arraySupport : ArrayList<String> = ArrayList()
                    arraySupport.add(question)
                    arraySupport.add(answer1)
                    arraySupport.add(answer2)
                    arraySupport.add(answer3)
                    arraySupport.add(correctAnswer)

                    refreshDatabase(arraySupport)
                }
            }

        adapter.notifyDataSetChanged()

    }
}