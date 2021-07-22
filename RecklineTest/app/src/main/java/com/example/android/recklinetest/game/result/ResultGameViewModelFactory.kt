package com.example.android.recklinetest.game.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultGameViewModelFactory(private val rightAnswers: Int, private val totalQuestions: Int) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultGameViewModel::class.java)) {
            return ResultGameViewModel(rightAnswers, totalQuestions) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}