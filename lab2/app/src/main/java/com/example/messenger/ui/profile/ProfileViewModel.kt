package com.example.messenger.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val tag = "ProfileViewModel"

    val name = MutableLiveData("Иван Иванов")
    val status = MutableLiveData("Привет! Я использую Messenger")

    init {
        Log.d(tag, "created")
    }

    fun updateName(newName: String) {
        if (name.value != newName) name.value = newName
    }

    fun updateStatus(newStatus: String) {
        if (status.value != newStatus) status.value = newStatus
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(tag, "onCleared")
    }
}
