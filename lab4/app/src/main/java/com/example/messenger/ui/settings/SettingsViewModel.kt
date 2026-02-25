package com.example.messenger.ui.settings

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val tag = "SettingsViewModel"

    val isDarkTheme = MutableLiveData(
        AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    )

    init { Log.d(tag, "created") }

    fun setDarkTheme(enabled: Boolean) {
        if (isDarkTheme.value == enabled) return
        isDarkTheme.value = enabled
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    override fun onCleared() { super.onCleared(); Log.d(tag, "onCleared") }
}
