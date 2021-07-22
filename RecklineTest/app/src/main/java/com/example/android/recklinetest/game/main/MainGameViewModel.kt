package com.example.android.recklinetest.game.main

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Math.abs
import kotlin.random.Random

class MainGameViewModel : ViewModel() {

    //region timer
    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 60000L
    }

    private val timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
        override fun onTick(millisUntilFinished: Long) {
            _currentTime.value = (millisUntilFinished / ONE_SECOND).toString()
        }

        override fun onFinish() {
            _currentTime.value = DONE.toString()
            viewModelScope.launch {
                eventChannel.send(Event.NavigateToResult)
            }
        }
    }

    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String>
        get() = _currentTime
    //endregion

    //region events
    sealed class Event {
        object NavigateToResult : Event()
        object CorrectAnswer : Event()
        object IncorrectAnswer : Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()
    //endregion

    //region example display
    enum class Operation(val str: String) {
        ADDITION("+"),
        SUBTRACTION("-"),
        DIVISION("/"),
        MULTIPLICATION("*")
    }

    private var currentExampleString = ""

    private val _currentExampleWithAnswerString = MutableLiveData<String>()
    val currentExampleWithAnswerString: LiveData<String>
        get() = _currentExampleWithAnswerString

    private fun nextExample() {
        currentUserAnswer = -1
        val firstNumber = Random.nextInt(0, 100)
        val secondNumber = Random.nextInt(0, 100)

        when (Operation.values()[Random.nextInt(0, 4)]) {
            Operation.ADDITION -> {
                currentCorrectAnswer = firstNumber + secondNumber
                currentExampleString =
                    formExampleString(firstNumber, secondNumber, Operation.ADDITION)
            }
            Operation.SUBTRACTION -> {
                currentCorrectAnswer = abs(secondNumber - firstNumber)
                currentExampleString = formExampleString(
                    maxOf(firstNumber, secondNumber),
                    minOf(firstNumber, secondNumber),
                    Operation.SUBTRACTION
                )
            }
            Operation.DIVISION -> {
                currentCorrectAnswer = firstNumber
                currentExampleString = formExampleString(
                    firstNumber * secondNumber,
                    secondNumber,
                    Operation.DIVISION
                )
            }

            Operation.MULTIPLICATION -> {
                currentCorrectAnswer = firstNumber * secondNumber
                currentExampleString =
                    formExampleString(firstNumber, secondNumber, Operation.MULTIPLICATION)
            }
        }
        _currentExampleWithAnswerString.value = currentExampleString
    }

    private fun formExampleString(
        firstNumber: Int,
        secondNumber: Int,
        operation: Operation
    ): String {
        return "$firstNumber${operation.str}$secondNumber="
    }
    //endregion

    //region score display
    private var currentCorrectAnswer = -1
    private var currentUserAnswer = -1

    var rightAnswers = 0
    var totalQuestions = 0

    private val _currentScore = MutableLiveData<String>()
    val currentScore: LiveData<String>
        get() = _currentScore
    //endregion

    //region keyboard buttons method
    fun keyboardPressedNumber(number: Int) {
        if(currentUserAnswer.toString().length == 6)
            return
        if (currentUserAnswer == -1) {
            currentUserAnswer = number
        }
        else {
            currentUserAnswer *= 10
            currentUserAnswer += number
        }
        _currentExampleWithAnswerString.value = currentExampleString + currentUserAnswer.toString()
    }

    fun keyboardPressedBackspace() {
        currentUserAnswer /= 10
        if(currentUserAnswer > 0) {
            _currentExampleWithAnswerString.value = currentExampleString + currentUserAnswer.toString()
        }
        else _currentExampleWithAnswerString.value = currentExampleString

    }

    fun keyboardPressedCheckMark() {
        if(currentUserAnswer == currentCorrectAnswer) {
            rightAnswers++
            totalQuestions++
            viewModelScope.launch {
                eventChannel.send(Event.CorrectAnswer)
            }
        }
        else {
            totalQuestions++
            viewModelScope.launch {
                eventChannel.send(Event.IncorrectAnswer)
            }
        }
        _currentScore.value = "$rightAnswers/$totalQuestions"
        nextExample()
    }
    //endregion

    init {
        timer.start()
        nextExample()
        _currentScore.value = "$rightAnswers/$totalQuestions"
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

}