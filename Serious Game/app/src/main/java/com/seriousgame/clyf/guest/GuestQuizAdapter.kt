package com.seriousgame.clyf.guest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seriousgame.clyf.R
import com.seriousgame.clyf.admin.QuizAdapter

class GuestQuizAdapter(private var x: ArrayList<ArrayList<String>>, private var context: Context) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.question_row, null, false)
        return QuizAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuizAdapter.ViewHolder, position: Int) {
        holder.arrayStructure(x, position)
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

        fun arrayStructure(x : ArrayList<ArrayList<String>>, y : Int){
            var arraySupport = x.get(y)
            question.text = arraySupport.get(0)
            answer1.text = arraySupport.get(1)
            answer2.text = arraySupport.get(2)
            answer3.text = arraySupport.get(3)
            correctAnswer.text = arraySupport.get(4)
        }

    }

}