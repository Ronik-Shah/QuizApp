package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quizapp.models.StaticData
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        score.text = intent.getIntExtra("Score",0).toString()
        initializeValues()
    }

    fun restartQuiz(view : View){
          startActivity(Intent(this,MainActivity::class.java))
    }

    private fun initializeValues(){
        StaticData.correctAnswers.clear()
        StaticData.options.clear()
        StaticData.questions.clear()
        StaticData.score = 0
        StaticData.count = 0
    }

}
