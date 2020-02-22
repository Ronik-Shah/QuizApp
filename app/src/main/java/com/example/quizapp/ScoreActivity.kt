package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        score.text = intent.getIntExtra("Score",0).toString()
    }

    fun restartQuiz(view : View){
//        i.putExtra("Questions", quizQuestions.questions as Serializable)
//        i.putExtra("CorrectAnswers",quizQuestions.correctAnswers as Serializable)
//        i.putExtra("Options",quizQuestions.options as Serializable)

    }

}
