package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.database.entity.ReserveMovie


@Dao
interface ReserveMovieDao {

    @Query("SELECT * FROM reservemovie")
    fun getAll():List<ReserveMovie>

    @Query("SELECT * FROM reservemovie WHERE movieName=:movieName")
    fun getReserveMovie(movieName: String):ReserveMovie?

    @Query("INSERT INTO reservemovie(posterPath,movieName,movieInfo) VALUES(:posterPath,:movieName,:movieInfo)")
    fun insertMovie(posterPath:String,movieName:String,movieInfo:String)

    @Query("DELETE FROM reservemovie WHERE movieName=:movieName")
    fun deleteMovie(movieName: String)

}