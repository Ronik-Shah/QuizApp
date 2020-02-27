package com.example.quizapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.quizapp.models.DifficultyModel
import com.example.quizapp.models.StaticData
import com.example.quizapp.widgets.DifficultyAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder
import java.util.*


class MainActivity : AppCompatActivity() {

    private var models = ArrayList<DifficultyModel>()
    private lateinit var adapter: DifficultyAdapter
    private var colors = ArrayList<Int>()
    private var difficultyLevels = Array(3) { i -> "$i"}
    private lateinit var difficulty : String

    inner class JsonDataRetrieval : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg strings: String?): String? {
            val uri: URL
            val api = StringBuffer()
            val httpURLConnection: HttpURLConnection
            return try {
                uri = URL(strings[0])
                httpURLConnection = uri.openConnection() as HttpURLConnection
                val isr =
                    InputStreamReader(httpURLConnection.inputStream)
                var data = isr.read()
                while (data >= 0) {
                    api.append(data.toChar())
                    data = isr.read()
                }
                api.toString()
            } catch (IOException: Exception) {
                IOException.printStackTrace()
                "FAILED : MALFORMED EXCEPTION"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeDifficultyCards()
        difficultyLevels[0] = "easy"
        difficultyLevels[1] = "medium"
        difficultyLevels[2] = "hard"
        difficulty = difficultyLevels[0]

    }

    private fun initializeDifficultyCards() {
        models.add(DifficultyModel("Easy", R.color.easyColor))
        models.add(DifficultyModel("Medium", R.color.mediumColor))
        models.add(DifficultyModel("Hard", R.color.hardColor))
        adapter = DifficultyAdapter(models, this)
        colors.add(resources.getColor(R.color.easyColor))
        colors.add(resources.getColor(R.color.mediumColor))
        colors.add(resources.getColor(R.color.hardColor))
        difficulty_view_pager.adapter = DifficultyAdapter(models, this)
        difficulty_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position < adapter.count - 1 && position < colors.size - 1) {
                    main_activity_constraint_layout.setBackgroundColor(colors[position])
                    start_button.setBackgroundColor(colors[position])
                }else{
                    main_activity_constraint_layout.setBackgroundColor(colors[colors.size - 1])
                    start_button.setBackgroundColor(colors[colors.size-1])
                }
            }

            override fun onPageSelected(position: Int) {
                difficulty = difficultyLevels[position]
                println(difficulty)
            }

        })
    }

    fun startQuiz(view: View) {
        val api = JsonDataRetrieval()
        //            String encoded = URLEncoder.encode(city.getText().toString(),"UTF-8");
        val result = api.execute(
            "https://opentdb.com/api.php?amount=10&" + (10 + 9) + "&" + difficulty + "&type=multiple&encode=url3986"
        ).get()
        onPostExecute(result)
        val i = Intent(this, QuizActivity::class.java)
        startActivity(i)
    }

    //
    private fun onPostExecute(s: String) {
        try { //                    System.out.println(s);
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
                    StaticData.correctAnswers.add(correctAnswer)
                    val incorrectAnswers =
                        Array(3) { index -> "Option $index" }
                    val temp2 = temp.getJSONArray("incorrect_answers")
                    for (j in 0..2) {
                        incorrectAnswers[j] =
                            URLDecoder.decode(temp2[j] as String, "UTF-8")
                        //                                Log.d("Option", incorrect_answers[j]);
                    }
                    var finalOptions =
                        Array(4) { index -> "Option $index" }
                    for (j in finalOptions.indices) {
                        if (j == finalOptions.size - 1) finalOptions[j] =
                            correctAnswer else finalOptions[j] = incorrectAnswers[j]
                    }
                    StaticData.questions.add(question)
                    Collections.shuffle(finalOptions.asList())
                    StaticData.options.add(finalOptions)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        //       progressDialog.dismiss();
    }

}
