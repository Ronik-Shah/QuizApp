package com.example.quizapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.models.StaticData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    var selectedOptions = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        questionNumber.text = "Question-1"
        questionContent.text = StaticData.questions[0]
        radioButtonA.text = StaticData.options[0][0]
        radioButtonB.text = StaticData.options[0][1]
        radioButtonC.text = StaticData.options[0][2]
        radioButtonD.text = StaticData.options[0][3]

        optionsRadioButton.clearCheck()
    }

    fun toPrevQuestion(view: View) {
        if (StaticData.count == 0) {
            Snackbar.make(
                window.decorView.rootView,
                "This is the first Question",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            setText()
            selectOption()
            optionsRadioButton.clearCheck()
            StaticData.count--
        }
    }

    private fun selectOption() {
        val id = optionsRadioButton.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(id)
        if (radioButton == null) {
            Snackbar.make(
                window.decorView.rootView,
                "No option selected",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            selectedOptions.add(radioButton.text.toString())
        }
    }

    fun toNextQuestion(view: View) {
        if (StaticData.count == StaticData.questions.size) {
            computeScore()
            startActivity(Intent(this, ScoreActivity::class.java))
        } else {
            setText()
            selectOption()
            optionsRadioButton.clearCheck()
            StaticData.count++
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
        for(i in selectedOptions){
            Log.i("Selected Answers",i)
        }

        for(i in StaticData.correctAnswers){
            Log.i("Correct Answers",i)
        }

        for (i in 0 until selectedOptions.size) {
            if (selectedOptions[i] == StaticData.correctAnswers[i]) {
                StaticData.score += 2
            }
        }
    }

    override fun onDestroy() {
        computeScore()
        super.onDestroy()
    }
}
