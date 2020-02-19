package com.example.quizapp

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.models.APIData
import com.example.quizapp.models.JsonDataRetrieval
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private var count = 0
    private var questions = ArrayList<String>()
    private var correctAnswers = ArrayList<String>()
    private var options = ArrayList<Array<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val quizQuestions = APIData(10, window.decorView.rootView)
        questions = quizQuestions.questions
        options = quizQuestions.options
        correctAnswers = quizQuestions.correctAnswers
    }

    fun changeQuizQuestion(view: View) {
        if (count >= questions.size) {
            val i = Intent(this, ScoreActivity::class.java)
            startActivity(i)
            return
        }
        if (count < questions.size) {
            println("IF A")
            questionNumber.text = "Question-" + (count + 1)
            questionContent.text = questions[count]
            radioButtonA.text = options[count][0]
            radioButtonB.text = options[count][1]
            radioButtonC.text = options[count][2]
            radioButtonD.text = options[count][3]
            if (optionsRadioButton.checkedRadioButtonId == -1) {
                println("If B")
                Snackbar.make(
                    findViewById(R.id.linear_layout),
                    "Please Select a option",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
                count--

            } else {
                println("IF C")
                optionsRadioButton.clearCheck()
            }
        }
        count++
    }
}
