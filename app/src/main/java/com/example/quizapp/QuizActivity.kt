package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {


    var count = 0
    var score = 0
    private var questions = ArrayList<String>()
    private var correctAnswers = ArrayList<String>()
    private var options = ArrayList<Array<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        questions = intent.getStringArrayListExtra("Questions")
        options = intent.getSerializableExtra("Options") as ArrayList<Array<String>>
        correctAnswers = intent.getStringArrayListExtra("CorrectAnswers")
        questionNumber.text = "Question-" + 1
        questionContent.text = questions[0]
        radioButtonA.text = options[0][0]
        radioButtonB.text = options[0][1]
        radioButtonC.text = options[0][2]
        radioButtonD.text = options[0][3]
    }

    fun nextQuizQuestion(view: View) {
        if (count >= questions.size - 1) {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("Score", score)
            startActivity(intent)
        } else {
            count++
            setText()
            computeScore()
        }
        optionsRadioButton.clearCheck()
    }

    fun previousQuizQuestion(view: View) {
        optionsRadioButton.clearCheck()
        if (count <= 0) {
            Snackbar.make(
                window.decorView.rootView,
                "This is the first question",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            count--
            setText()
            computeScore()
        }
    }

    private fun setText() {
        questionNumber.text = "Question-" + (count + 1)
        questionContent.text = questions[count]
        radioButtonA.text = options[count][0]
        radioButtonB.text = options[count][1]
        radioButtonC.text = options[count][2]
        radioButtonD.text = options[count][3]
    }

    private fun computeScore() {
        val id = optionsRadioButton.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(id)
        if (radioButton == null) {
            Snackbar.make(
                window.decorView.rootView,
                "No option selected",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            Log.i("RadioButtonText", radioButton.text.toString())
            Log.i("CorrectAnswer", correctAnswers[count])
            if (radioButton.text.toString() == correctAnswers[count]) {
                score += 2
            }
            println(score)
        }
    }
}
