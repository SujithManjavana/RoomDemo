package com.sujith.roomdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase() {
    abstract val subscriberDAO: SubscriberDAO

    companion object {
        private var INSTANCE: SubscriberDatabase? = null

        fun getInstance(ctx: Context): SubscriberDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        ctx.applicationContext,
                        SubscriberDatabase::class.java,
                        "subscriber_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
