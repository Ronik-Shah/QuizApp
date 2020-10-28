package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quizapp.models.StaticData
import kotlinx.android.synthetic.main.activity_score.*
import java.util.*

class ScoreActivity : AppCompatActivity(),View.OnClickListener {
    private var correctAnswers = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        computeScore()
        setContentView(R.layout.activity_score)
        restartButton.setOnClickListener(this)
        score_count.text = StaticData.score.toString()
    }

    private fun initializeValues(){
        StaticData.questions.clear()
        StaticData.selectedOptions.clear()
        StaticData.score = 0
        StaticData.count = 0
    }

    private fun computeScore(){
        for(i in StaticData.questions.indices){
            correctAnswers.add(StaticData.questions[i].getCorrectAnswer())
        }
//        Log.i("SELECTED OPTIONS", "Size :" + StaticData.selectedOptions.size + "\nList :" + Arrays.toString(StaticData.selectedOptions.toArray()))
//        Log.i("CORRECT OPTIONS", "Size :" + correctAnswers.size + "\nList :" + Arrays.toString(correctAnswers.toArray()))
        for(i in StaticData.selectedOptions.indices){
            if(StaticData.selectedOptions[i] == correctAnswers[i]) StaticData.score++
        }
    }

    override fun onClick(p0: View?) {
        if(p0 != null){
            if(p0.id == restartButton.id){
                initializeValues()
                startActivity(Intent(this,MainActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            }
        }
    }

    override fun onBackPressed() {
        initializeValues()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

    }

}
