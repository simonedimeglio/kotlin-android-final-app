package com.seriousgame.clyf.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.seriousgame.clyf.R
import com.seriousgame.clyf.auth.db
import com.seriousgame.clyf.auth.quizBlock
import com.seriousgame.clyf.auth.supportID

class QuizAdapter(private var x: ArrayList<String>, private var context: Context) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.question_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.question.text = x.get(position)
        holder.answer1.text = x.get(position)
        holder.answer2.text = x.get(position)
        holder.answer3.text = x.get(position)
        holder.correctAnswer.text = x.get(position)
    }

    override fun getItemCount(): Int {
        return x.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question : TextView = itemView.findViewById(R.id.questionView)
        val answer1 : TextView = itemView.findViewById(R.id.answer1View)
        val answer2 : TextView = itemView.findViewById(R.id.answer2View)
        val answer3 : TextView = itemView.findViewById(R.id.answer3View)
        val correctAnswer : TextView = itemView.findViewById(R.id.correctAnswerView)
    }

}
