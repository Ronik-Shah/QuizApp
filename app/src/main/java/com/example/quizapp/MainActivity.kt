package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
//import com.example.quizapp.models.APIData
//import java.io.Serializable
//import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startQuiz(view : View){
//        println(quizQuestions.questions[0])
//        println(quizQuestions.correctAnswers[0])
//        println(quizQuestions.options[0][0])
        val i = Intent(this,QuizActivity::class.java)
//        i.putStringArrayListExtra("Questions",quizQuestions.questions)
//        i.putStringArrayListExtra("CorrectAnswers",quizQuestions.correctAnswers)
//        i.putExtra("Options",quizQuestions.options as Serializable)
        startActivity(i)
    }
}
