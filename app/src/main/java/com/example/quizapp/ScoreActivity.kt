package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
//import com.example.quizapp.models.APIData
//import java.io.Serializable

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
    }

    fun restartQuiz(view : View){
//        val quizQuestions = APIData(10,window.decorView.rootView)
        val i = Intent(this,QuizActivity::class.java)
//        i.putExtra("Questions", quizQuestions.questions as Serializable)
//        i.putExtra("CorrectAnswers",quizQuestions.correctAnswers as Serializable)
//        i.putExtra("Options",quizQuestions.options as Serializable)
        startActivity(i)
    }

}
