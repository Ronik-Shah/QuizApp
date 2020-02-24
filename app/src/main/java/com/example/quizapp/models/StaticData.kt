package com.example.quizapp.models

class StaticData {
    companion object{
        var count = 0
        var questions = ArrayList<String>()
        var correctAnswers = ArrayList<String>()
        val options = ArrayList<Array<String>>()
        var score = 0
        var socreList = ArrayList<String>()
    }
}