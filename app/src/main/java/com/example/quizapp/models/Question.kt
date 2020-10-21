package com.example.quizapp.models

import java.util.*

class Question(
    private var question: String,
    private var incorrectAnswers: Array<String>,
    private var correctAnswer: String
) {

    private var options = arrayOf("", "", "", "")

    fun getQuestion(): String {
        return question
    }

    fun getOptions(): Array<String> {
        options[0] = incorrectAnswers[0]
        options[1] = incorrectAnswers[1]
        options[2] = incorrectAnswers[2]
        options[3] = correctAnswer
        Collections.shuffle(options.asList())
        return options
    }

    fun getCorrectAnswer(): String {
        return correctAnswer
    }

    fun getCorrectAnswerIndex(): Int {
        var correctAnswerIndex = -1
        for (i in options.indices) {
            if (options[i] == correctAnswer) {
                correctAnswerIndex = i
                break
            }
        }
        return correctAnswerIndex
    }
}