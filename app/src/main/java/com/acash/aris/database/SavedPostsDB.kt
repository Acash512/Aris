package com.acash.aris.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acash.aris.models.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class SavedPostsDB:RoomDatabase() {
    abstract fun savedPostsDao():SavedPostsDao

    companion object {
        @Volatile
        private var Instance: SavedPostsDB? = null
        fun getDatabase(context: Context): SavedPostsDB {
            if (Instance != null) {
                return Instance!!
            }

            synchronized(this) {
                Instance = Room.databaseBuilder(
                    context,
                    SavedPostsDB::class.java, "SavedPosts.db"
                )
                    .fallbackToDestructiveMigration().build()
                return Instance!!
            }

        }
    }
}