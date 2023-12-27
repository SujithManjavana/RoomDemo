package com.sujith.roomdemo.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber)
}