package com.seriousgame.clyf.guest


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.seriousgame.clyf.MainActivity
import com.seriousgame.clyf.R
import com.seriousgame.clyf.auth.db
import com.seriousgame.clyf.auth.scores
import com.seriousgame.clyf.auth.supportID
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderboardActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LeaderboardAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        var home = homeLeaderboardButton
        recyclerView = findViewById(R.id.leaderboardRecycleView)
        layoutManager = LinearLayoutManager(this)
        adapter = LeaderboardAdapter(scores, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}