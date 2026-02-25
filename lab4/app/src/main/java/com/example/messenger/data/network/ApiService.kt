package com.example.messenger.data.network

import com.example.messenger.data.model.Message
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Message>
}
