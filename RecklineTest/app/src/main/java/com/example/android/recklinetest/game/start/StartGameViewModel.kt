package com.example.android.recklinetest.game.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.recklinetest.util.SharedPreferencesManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class StartGameViewModel : ViewModel() {

    sealed class Event {
        object NavigateToMain: Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun navigateToMainButtonClicked() {
        viewModelScope.launch {
            eventChannel.send(Event.NavigateToMain)
        }
    }
    var record = SharedPreferencesManager.getRecord()

    fun updateRecord() {
        record = SharedPreferencesManager.getRecord()
    }



}