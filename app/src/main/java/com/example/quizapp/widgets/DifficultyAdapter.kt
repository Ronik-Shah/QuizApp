package com.example.quizapp.widgets

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.models.DifficultyModel

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Text View
    var textView: TextView = view
        .findViewById(R.id.difficulty_item_card_text)
    var cardView : CardView = view.findViewById(R.id.difficulty_item_card_view)

}


class DifficultyAdapter : RecyclerView.Adapter<ViewHolder>{

    var model = ArrayList<DifficultyModel>()
    var context : Context

    constructor(model : ArrayList<DifficultyModel>, context : Context){
        this.model = model
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.difficulty_item_card,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = model[position].title
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, model[position].color))
    }

}


