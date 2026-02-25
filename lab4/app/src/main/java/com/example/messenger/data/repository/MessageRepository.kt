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
            val likedIds = dao.getAll().filter { it.isLiked }.map { it.id }.toSet()
            val messages = apiService.getPosts()
            dao.deleteAll()
            dao.insertAll(messages)
            if (likedIds.isNotEmpty()) {
                likedIds.forEach { id -> dao.updateLike(id, true) }
            }
            dao.getAll()
        } else {
            dao.getAll()
        }
    }

    suspend fun getCachedMessages(): List<Message> = dao.getAll()

    suspend fun toggleLike(message: Message) {
        dao.updateLike(message.id, !message.isLiked)
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
               caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
               caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
