package com.example.quizapp

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.models.*
import com.example.quizapp.models.SpinnerAdapter
import com.google.android.material.snackbar.Snackbar
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
import kotlin.collections.ArrayList
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private var difficultyLevels = ArrayList<String>()
    private var categories = ArrayList<String>()
    private lateinit var difficulty : String
    private var category : Int = 0
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
        for(i in EnumCategory.values()) {
            categories.add(i.name)
        }
        difficultyLevels.add("Easy")
        difficultyLevels.add("Medium")
        difficultyLevels.add("Hard")
        val da = SpinnerAdapter(this, R.layout.spinner_item,difficultyLevels)
        val ca = SpinnerAdapter(this,R.layout.spinner_item,categories)
        difficulty_drop_down.adapter = da
        category_drop_down.adapter = ca
        category_drop_down.onItemSelectedListener = this
        difficulty_drop_down.onItemSelectedListener = this
    }

    fun startQuiz(view: View) {
        val api = JsonDataRetrieval()
        val result = api.execute(
            "https://opentdb.com/api.php?amount=10&category=$category&difficulty=${difficulty.toLowerCase(
                Locale.ROOT
            )}&type=multiple&encode=url3986"
        ).get()
        onPostExecute(result)
        if (StaticData.questions.size > 0) {
            val i = Intent(this, QuizActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }else{
            Snackbar.make(view,"Sorry an internal error occured", Snackbar.LENGTH_SHORT).show()
        }
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
                    val question = URLDecoder.decode(temp.getString("question"), "UTF-8")
                    val correctAnswer = URLDecoder.decode(temp.getString("correct_answer"), "UTF-8")
                    val incorrectAnswers = arrayOf("","","","")
                    val temp2 = temp.getJSONArray("incorrect_answers")
                    for (j in 0 until temp2.length()) {
                        incorrectAnswers[j] = (URLDecoder.decode(temp2[j] as String, "UTF-8"))
                    }
                    val questionObj = Question(question,incorrectAnswers,correctAnswer)
                    StaticData.questions.add(questionObj)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(p0 != null){
            if(p0.id == category_drop_down.id){
                category = p2 + 9
            }
            if(p0.id == difficulty_drop_down.id){
                difficulty = difficultyLevels[p2]
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        if(p0 != null){
            if(p0.id == category_drop_down.id){
                Snackbar.make(main_activity_constraint_layout.rootView,"Please select a category", Snackbar.LENGTH_SHORT).show()
            }
            if(p0.id == difficulty_drop_down.id){
                Snackbar.make(main_activity_constraint_layout.rootView,"Please select a difficulty", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
        dialog.setIcon(android.R.drawable.ic_dialog_alert)
        dialog.setTitle("Exit")
        dialog.setMessage("Are you sure want to exit!")
        dialog.setPositiveButton("YES") { _, _ -> exitProcess(0) }
        dialog.setNegativeButton("NO"){dia,_ -> dia.cancel()}
        dialog.show()
    }
}

