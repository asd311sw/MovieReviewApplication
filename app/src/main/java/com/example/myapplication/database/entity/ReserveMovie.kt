package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ReserveMovie(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val posterPath:String,
    val movieName:String,
    val movieInfo:String
)
