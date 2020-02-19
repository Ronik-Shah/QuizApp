package com.example.quizapp.models;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.android.material.snackbar.Snackbar;

public class APIData implements Serializable{

    private int category;
    private  ArrayList<String> questions = new ArrayList<>();
    private  ArrayList<String[]> options = new ArrayList<>();
    private  ArrayList<String> correctAnswers = new ArrayList<>();
    private  View view;

    public APIData(int category,View view){
        getQuizQuestions();
        this.category = category;
        this.view = view;
    }

    private void getQuizQuestions(){
        JsonDataRetrieval api = new JsonDataRetrieval();
        try {
//            String encoded = URLEncoder.encode(city.getText().toString(),"UTF-8");
            api.execute("https://opentdb.com/api.php?amount=10&" + (category + 9) + "&type=multiple&encode=url3986").get();
            questions = api.getQuestions();
            options = api.getOptions();
            correctAnswers = api.getCorrectAnswers();
        }catch (Exception e){
            Snackbar.make(view,"Sorry for the inconvenience",Snackbar.LENGTH_SHORT);
        }
    }

    public ArrayList<String> getQuestions() { return questions; }

    public ArrayList<String[]> getOptions() {
        return options;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }
}
