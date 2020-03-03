package com.example.quizapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.difficulty_item_card.*

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.InputStreamReader
import java.io.UnsupportedEncodingException

import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder
import java.util.*

import com.example.quizapp.models.CategoryModel
import com.example.quizapp.models.DifficultyModel
import com.example.quizapp.models.StaticData
import com.example.quizapp.widgets.CategoryAdapter
import com.example.quizapp.widgets.DifficultyAdapter
import com.example.quizapp.models.EnumCategory


class MainActivity : AppCompatActivity() {

    private var difficultyModels = ArrayList<DifficultyModel>()
    private var categoryModels = ArrayList<CategoryModel>()
    private lateinit var difficultyAdapter: DifficultyAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var difficultyLevels = Array(3) { i -> "$i"}
    private var DIFFICULTY_COLOR_ARRAY  = arrayOf(R.color.easyColor,R.color.mediumColor,R.color.hardColor)
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
        difficultyLevels[0] = "easy"
        difficultyLevels[1] = "medium"
        difficultyLevels[2] = "hard"
        difficulty = difficultyLevels[0]
        initializeDifficultyCards()
        initializeCategoryCards()
    }

    private fun initializeCategoryCards() {
        categoryModels.add(CategoryModel("Mathematics",R.color.easyColor)) //R.color.easyColor
        categoryModels.add(CategoryModel("Science",R.color.mediumColor)) //R.color.easyColor
        categoryModels.add(CategoryModel("General Knowledge",R.color.hardColor)) //R.color.easyColor
        categoryAdapter = CategoryAdapter(categoryModels, this)
        category_view_pager.adapter = CategoryAdapter(categoryModels, this)
        category_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position < categoryAdapter.count - 1 && position < DIFFICULTY_COLOR_ARRAY.size - 1) {
                    setBackgroundColor(position)
                }else{
                    setBackgroundColor(DIFFICULTY_COLOR_ARRAY.size - 1)
                }
            }

            override fun onPageSelected(position: Int) {
            }

        })

    }

    private fun initializeDifficultyCards() {
        difficultyModels.add(DifficultyModel("Easy", DIFFICULTY_COLOR_ARRAY[0])) //R.color.easyColor
        difficultyModels.add(DifficultyModel("Medium", DIFFICULTY_COLOR_ARRAY[1])) //R.color.mediumColor
        difficultyModels.add(DifficultyModel("Hard", DIFFICULTY_COLOR_ARRAY[2])) //R.color.hardColor
        difficultyAdapter = DifficultyAdapter(difficultyModels, this)
        difficulty_view_pager.adapter = DifficultyAdapter(difficultyModels, this)
        difficulty_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position < difficultyAdapter.count - 1 && position < DIFFICULTY_COLOR_ARRAY.size - 1) {
                    setBackgroundColor(position)
                }else{
                    setBackgroundColor(DIFFICULTY_COLOR_ARRAY.size - 1)
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

    private fun setBackgroundColor(position : Int){
        main_activity_constraint_layout.setBackgroundColor(ContextCompat.getColor(applicationContext,DIFFICULTY_COLOR_ARRAY[position]))
        start_button.setBackgroundColor(ContextCompat.getColor(applicationContext,DIFFICULTY_COLOR_ARRAY[position]))
        difficulty_item_card_layout.setBackgroundColor(ContextCompat.getColor(applicationContext,DIFFICULTY_COLOR_ARRAY[position]))
        difficulty_view_pager.setBackgroundColor(ContextCompat.getColor(applicationContext,DIFFICULTY_COLOR_ARRAY[position]))

    }
}
