package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.models.StaticData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setText()
        optionsRadioButton.clearCheck()
    }

    fun nextQuizQuestion(view: View) {
        if (StaticData.count >= StaticData.questions.size - 1) {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("Score", StaticData.score)
            startActivity(intent)
        } else {
            setText()
            computeScore()
            StaticData.count++
        }
    }

    fun previousQuizQuestion(view: View) {
        optionsRadioButton.clearCheck()
        if (StaticData.count <= 0) {
            Snackbar.make(
                window.decorView.rootView,
                "This is the first question",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            setText()
            computeScore()
            StaticData.count--
        }
    }

    private fun setText() {
        questionNumber.text = "Question- ${StaticData.count + 1}"
        questionContent.text = StaticData.questions[StaticData.count]
        radioButtonA.text = StaticData.options[StaticData.count][0]
        radioButtonB.text = StaticData.options[StaticData.count][1]
        radioButtonC.text = StaticData.options[StaticData.count][2]
        radioButtonD.text = StaticData.options[StaticData.count][3]
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
            Log.i("CorrectAnswer", StaticData.correctAnswers[StaticData.count])
            if (radioButton.text.toString() == StaticData.correctAnswers[StaticData.count]) {
                StaticData.score += 2
            }
            Log.i("RadioButtonText", radioButton.text.toString())
            println(StaticData.score)
        }
        optionsRadioButton.clearCheck()
    }
}
