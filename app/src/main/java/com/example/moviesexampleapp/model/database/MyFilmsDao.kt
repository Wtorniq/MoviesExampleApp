package com.example.moviesexampleapp.model.database

import androidx.room.*
import com.example.moviesexampleapp.model.entities.MovieState

@Dao
interface MyFilmsDao {

    @Query("SELECT * FROM MyFilmEntity")
    fun all():List<MyFilmEntity>

    @Query("SELECT * FROM MyFilmEntity WHERE state = 'STATE_WANTED'")
    fun wanted():List<MyFilmEntity>

    @Query("SELECT * FROM MyFilmEntity WHERE state = 'STATE_VIEWED'")
    fun viewed():List<MyFilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: MyFilmEntity)

    @Query("UPDATE MyFilmEntity SET state = :state WHERE kinopoiskId = :id")
    fun updateEntity(id: Int, state: MovieState)

    @Query("DELETE FROM MyFilmEntity WHERE kinopoiskId = :id")
    fun delete(id : Int)

    @Query("DELETE FROM MyFilmEntity")
    fun deleteAll()

}