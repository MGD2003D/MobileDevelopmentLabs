package com.example.messenger.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.messenger.data.local.MessageDao
import com.example.messenger.data.model.Message
import com.example.messenger.data.network.ApiService

class MessageRepository(
    private val apiService: ApiService,
    private val dao: MessageDao,
    private val context: Context
) {

    suspend fun getMessages(): List<Message> {
        return if (isNetworkAvailable()) {
            val messages = apiService.getPosts()
            dao.deleteAll()
            dao.insertAll(messages)
            messages
        } else {
            dao.getAll()
        }
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
