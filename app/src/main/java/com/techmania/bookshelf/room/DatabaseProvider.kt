package com.techmania.bookshelf.room

import AppDatabase
import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "books_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}