package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.models.StaticData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var radioButtons: Array<RadioButton>
    private lateinit var options: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        radioButtons = arrayOf(radioButtonA, radioButtonB, radioButtonC, radioButtonD)
        nextButton.setOnClickListener(this)
        for (radioButton in radioButtons)
            radioButton.setOnClickListener(this)
        setQuestion()
    }

    private fun setQuestion() {
        val curr = StaticData.count
        options = StaticData.questions[curr].getOptions()
        questionNumber.text = "Question ${curr + 1}"
        questionContent.text = StaticData.questions[curr].getQuestion()
        for (i in radioButtons.indices) {
            radioButtons[i].text = options[i]
        }

    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            if (p0.id == nextButton.id) {
                val radioButtonId = options_radio_group.checkedRadioButtonId
                val index = options_radio_group.indexOfChild(findViewById(radioButtonId))
                if (index < 0) {
                    Snackbar.make(
                        p0,
                        "Please choose a option!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    StaticData.selectedOptions.add(options[index])
                    options_radio_group.clearCheck()
                    StaticData.count++
                    if(StaticData.count < 10) {
                        setQuestion()
                    } else {
                        val intent = Intent(this, ScoreActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        Snackbar.make(quiz_activity_layout.rootView,"Back is not allowed here!",Snackbar.LENGTH_SHORT).show()
    }
}
