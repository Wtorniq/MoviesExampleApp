package com.example.moviesexampleapp.model.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesexampleapp.App

@androidx.room.Database(
    entities = [
        MyFilmEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {
    abstract fun myFilmsDao(): MyFilmsDao

    companion object {
        private const val DB_NAME = "films_database.db"

        val db: Database by lazy {
            Room.databaseBuilder(
                App.appInstance,
                Database::class.java,
                DB_NAME
            ).build()
        }
    }
}