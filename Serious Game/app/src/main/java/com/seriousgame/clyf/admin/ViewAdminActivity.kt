package com.seriousgame.clyf.admin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.seriousgame.clyf.R
import com.seriousgame.clyf.auth.db
import com.seriousgame.clyf.auth.quizBlock
import com.seriousgame.clyf.auth.supportID
import kotlinx.android.synthetic.main.activity_view_admin.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ViewAdminActivity : AppCompatActivity() {


        lateinit var recyclerView: RecyclerView
        lateinit var refresh: Button
        lateinit var contenitore: ArrayList<String>
        lateinit var adapter: QuizAdapter
        lateinit var layoutManager: RecyclerView.LayoutManager

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_view_admin)

            // on below line we are initializing our variables.
            recyclerView = findViewById(R.id.recyclerViewId)
            refresh = findViewById(R.id.button5)
            contenitore = ArrayList()
            layoutManager = LinearLayoutManager(this)

            // on below line we are
            // adding data to our list.
            contenitore.add("a")
            contenitore.add("b")
            contenitore.add("c")
            contenitore.add("d")
            contenitore.add("d")

            // on below line we are adding our list to our adapter.
            adapter = QuizAdapter(contenitore, this)

            // on below line we are setting
            // adapter to our recycler view.
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager

            // on below line we are adding click listener
            // for our add button.
            refresh.setOnClickListener {
                lateinit var test : String
                db.collection(supportID).whereEqualTo("Question", "ciao3").get()
                    .addOnSuccessListener { result ->
                        for (document in result){
                            test = document.data["Question"].toString()
                        }
                        refreshDatabase(test)
                    }

            }
            // on below line we are notifying adapter
            // that data in adapter has been updated.
            adapter.notifyDataSetChanged()

        }

        // on below line we are creating a
        // new function to add item.
        private fun refreshDatabase(x: String) {
            // on below line we are checking
            // if item is empty or not.
            if (x.isNotEmpty()) {
                // on below line we are
                // adding item to our list
                contenitore.add(x)
                // on below line we are notifying
                // adapter that data has updated.
                adapter.notifyDataSetChanged()
            }
        }

}