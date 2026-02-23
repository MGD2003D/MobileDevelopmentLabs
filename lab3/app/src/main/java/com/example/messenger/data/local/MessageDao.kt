package com.example.messenger.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.messenger.data.model.Message

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    suspend fun getAll(): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<Message>)

    @Query("DELETE FROM messages")
    suspend fun deleteAll()
}
