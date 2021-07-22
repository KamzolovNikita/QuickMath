package com.example.android.recklinetest.game.result


import androidx.lifecycle.ViewModel
import com.example.android.recklinetest.util.SharedPreferencesManager

class ResultGameViewModel(val rightAnswers: Int, val totalQuestions: Int) :
    ViewModel() {

    val rightAnswersPercent = if (totalQuestions == 0) 0 else (rightAnswers * 100 / totalQuestions)

    val isNewRecord = SharedPreferencesManager.getRecord() < rightAnswers

    init {
        if(isNewRecord) {
            SharedPreferencesManager.saveNewRecord(rightAnswers)
        }
    }
}