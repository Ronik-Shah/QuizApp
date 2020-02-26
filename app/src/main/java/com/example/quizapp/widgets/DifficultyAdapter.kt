package com.example.quizapp.widgets

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.quizapp.R
import com.example.quizapp.models.DifficultyModel

class DifficultyAdapter(private var model: ArrayList<DifficultyModel>, private var context: Context) :
    PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` == view
    }

    override fun getCount(): Int {
        return model.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layout = LayoutInflater.from(context).inflate(R.layout.difficulty_item_card,container,false)
        val cardTextView : TextView = layout.findViewById(R.id.difficulty_item_card_text)
        val cardView : CardView = layout.findViewById(R.id.difficulty_item_card_view)

        cardTextView.text = model[position].title
        cardView.setCardBackgroundColor(model[position].color)
        container.addView(layout,0)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}


