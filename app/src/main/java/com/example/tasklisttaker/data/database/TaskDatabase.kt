package com.example.tasklisttaker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tasklisttaker.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "TaskDatabase_db"

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, TaskDatabase::class.java, DATABASE_NAME).build()
                INSTANCE = instance
                instance
            }
        }

    }
}