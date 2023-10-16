package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.database.entity.FavoriteMovie


@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favoritemovie")
    fun getAll():List<FavoriteMovie>

    @Query("SELECT * FROM favoritemovie WHERE movieName=:movieName")
    fun getFavoriteMovie(movieName: String):FavoriteMovie?

    @Query("INSERT INTO favoritemovie(posterPath,movieName,movieInfo) VALUES(:posterPath,:movieName,:movieInfo)")
    fun insertFavoriteMovie(posterPath:String,movieName:String,movieInfo:String)

    @Query("DELETE FROM favoritemovie WHERE movieName=:movieName")
    fun deleteMovie(movieName: String)


}