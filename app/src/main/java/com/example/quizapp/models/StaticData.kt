package com.example.quizapp.models

class StaticData {
    companion object{
        var count = 0
        var questions = ArrayList<Question>()
        var score = 0
        var socreList = ArrayList<String>()
        var selectedOptions = ArrayList<String>()
    }
}