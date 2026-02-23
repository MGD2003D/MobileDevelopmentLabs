package com.example.messenger.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.local.AppDatabase
import com.example.messenger.data.model.Message
import com.example.messenger.data.network.RetrofitClient
import com.example.messenger.data.repository.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(app: Application) : AndroidViewModel(app) {

    private val tag = "FeedViewModel"

    private val repository = MessageRepository(
        apiService = RetrofitClient.apiService,
        dao = AppDatabase.getInstance(app).messageDao(),
        context = app
    )

    val messages = MutableLiveData<List<Message>>()
    val isLoading = MutableLiveData(false)
    val statusMessage = MutableLiveData<String?>()

    init {
        Log.d(tag, "created")
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            statusMessage.value = null
            try {
                messages.value = repository.getMessages()
                if (!repository.isNetworkAvailable()) {
                    statusMessage.value = "Нет сети. Загружены кэшированные данные."
                }
            } catch (e: Exception) {
                Log.e(tag, "Error loading messages: ${e.message}")
                statusMessage.value = "Ошибка загрузки. Загружены кэшированные данные."
            } finally {
                isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(tag, "onCleared")
    }
}
