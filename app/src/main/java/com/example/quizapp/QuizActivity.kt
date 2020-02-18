package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.models.APIData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_quiz.*

enum class Category {
    GeneralKnowledge,
    Books ,
    Film ,
    Music,
    MusicalAndTheaters,
    Television,
    VideoGames,
    BoardGames,
    ScienceNature,
    Computers,
    Mathematics,
    Mythology,
    Sports,
    Geography,
    History,
    Politics,
    Art,
    Celebrities,
    Animals,
    Vehicles,
    Comics,
    Gadgets,
    JapaneseAnimeAndManga,
    CartoonAndAnimations
}

class QuizActivity : AppCompatActivity() {

    var count = 0
    var questions = ArrayList<String>()
    var correctAnswers = ArrayList<String>()
    var options = ArrayList<Array<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val quizQuestions = APIData(10, window.decorView.rootView)
        questions = quizQuestions.questions
        options = quizQuestions.options
        correctAnswers = quizQuestions.correctAnswers
//        questionNumber.text = "Question-" + 1
//        questionContent.text = questions[0]
//        radioButtonA.text = options[0][0]
//        radioButtonB.text = options[0][1]
//        radioButtonC.text = options[0][2]
//        radioButtonD.text = options[0][3]
    }

    fun changeQuestionAndOptions(view: View) {
        try {
            count++
            Snackbar.make(findViewById(R.id.linear_layout), "Next Question", Snackbar.LENGTH_SHORT)
                .show()
            questionNumber.text = "Question-" + (count + 1)
            questionContent.text = questions[count]
            radioButtonA.text = options[count][0]
            radioButtonB.text = options[count][1]
            radioButtonC.text = options[count][2]
            radioButtonD.text = options[count][3]
            optionsRadioButton.clearCheck()
        } catch (e: IndexOutOfBoundsException) {
            val i = Intent(this, ScoreActivity::class.java)
            startActivity(i)
        }
    }

}
