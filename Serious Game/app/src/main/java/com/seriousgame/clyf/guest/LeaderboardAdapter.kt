package com.seriousgame.clyf.guest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seriousgame.clyf.R
import com.seriousgame.clyf.admin.QuizAdapter

class LeaderboardAdapter (private var x: ArrayList<ArrayList<String>>, private var context: Context) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.score_row, null, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.arrayStructure(x, position)
    }

    override fun getItemCount(): Int {
        return x.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickname : TextView = itemView.findViewById(R.id.nicknameView)
        val score : TextView = itemView.findViewById(R.id.scoreView)

        fun arrayStructure(x : ArrayList<ArrayList<String>>, y : Int){
            var arraySupport = x.get(y)
            score.text = arraySupport.get(1)
            nickname.text = arraySupport.get(0)
        }

    }

}