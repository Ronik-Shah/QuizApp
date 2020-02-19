package com.example.quizapp.models;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JsonDataRetrieval extends AsyncTask<String,Integer,String> implements Serializable {

    private ArrayList<String> questions = new ArrayList<>();
    private  ArrayList<String[]> options = new ArrayList<>();
    private  ArrayList<String> correctAnswers = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... strings){
        URL uri;
        StringBuffer api = new StringBuffer();
        HttpURLConnection httpURLConnection;
        try{
            uri = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection)uri.openConnection();
            InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream());
            int data = isr.read();
            while (data >= 0){
                api.append((char) data);
                data = isr.read();
            }
            return api.toString();
        }catch (Exception IOException){
            IOException.printStackTrace();
            return "FAILED : MALFORMED EXCEPTION";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
//                    System.out.println(s);
            String response_code = new JSONObject(s).getString("response_code");
            if(!response_code.equals("0")) Log.i("INFO","Response Code not equal to 0");
            else {
                JSONArray results = new JSONArray(new JSONObject(s).getString("results"));
                for(int i=0;i<results.length();i++){
                    JSONObject temp = results.getJSONObject(i);
                    String question = URLDecoder.decode(temp.getString("question"),"UTF-8");
                    String correct_answer = URLDecoder.decode(temp.getString("correct_answer"),"UTF-8");
                    correctAnswers.add(correct_answer);
                    String[] incorrect_answers = new String[3];
                    JSONArray temp2 = temp.getJSONArray("incorrect_answers");
                    for(int j=0;j<3;j++){
                        incorrect_answers[j] = URLDecoder.decode((String)temp2.get(j),"UTF-8");
//                                Log.d("Option", incorrect_answers[j]);
                    }

                    String [] finalOptions = new String[4];
                    for(int j=0;j<finalOptions.length;j++){
                        if(j == finalOptions.length - 1)finalOptions[j] = correct_answer;
                        else finalOptions[j] = incorrect_answers[j];
                    }
                    questions.add(question);
                    Collections.shuffle(Arrays.asList(finalOptions));
                    options.add(finalOptions);
                }
            }
        }catch (JSONException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public ArrayList<String[]> getOptions() {
        return options;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }
}
