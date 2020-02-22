package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.models.JsonDataRetrieval
import com.example.quizapp.models.StaticData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*
import kotlin.collections.ArrayList

//import com.example.quizapp.models.APIData
//import java.io.Serializable
//import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private var questions = ArrayList<String>()
    private var correctAnswers = ArrayList<String>()
    private val options = ArrayList<Array<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startQuiz(view: View) {
        val api = JsonDataRetrieval()
        //            String encoded = URLEncoder.encode(city.getText().toString(),"UTF-8");
        val result = api.execute(
            "https://opentdb.com/api.php?amount=10&" + (10 + 9) + "&type=multiple&encode=url3986"
        ).get()
        onPostExecute(result)
        println(questions.size)
        val i = Intent(this, QuizActivity::class.java)
        i.putStringArrayListExtra("Questions",questions)
        i.putStringArrayListExtra("CorrectAnswers",correctAnswers)
        i.putExtra("Options",options)
        startActivity(i)
    }

    private fun onPostExecute(s: String) {
        try {
            val responseCode = JSONObject(s).getString("response_code")
            if (responseCode != "0") Log.i(
                "INFO",
                "Response Code not equal to 0"
            ) else {
                val results = JSONArray(JSONObject(s).getString("results"))
                for (i in 0 until results.length()) {
                    val temp = results.getJSONObject(i)
                    val question =
                        URLDecoder.decode(temp.getString("question"), "UTF-8")
                    val correctAnswer =
                        URLDecoder.decode(temp.getString("correct_answer"), "UTF-8")
                    correctAnswers.add(correctAnswer)
                    val incorrectAnswers =
                        Array(3) { index -> "Option $index" }
                    val temp2 = temp.getJSONArray("incorrect_answers")
                    for (j in 0..2) {
                        incorrectAnswers[j] =
                            URLDecoder.decode(temp2[j] as String, "UTF-8")
                        //                                Log.d("Option", incorrect_answers[j]);
                    }
                    val finalOptions = Array(4) { index -> "Option $index" }
                    for (j in finalOptions.indices) {
                        if (j == finalOptions.size - 1) finalOptions[j] =
                            correctAnswer else finalOptions[j] = incorrectAnswers[j]
                    }
                    questions.add(question)
                    listOf(*finalOptions).shuffled(Random(3))
                    options.add(finalOptions)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

    }
}
