package com.example.myapplication.dto

import com.example.myapplication.activity.MovieInfo
import com.example.myapplication.dto.detail.MovieDetails
import com.example.myapplication.dto.tmdb.MovieResult

data class MovieData(
    var movieInfo:MovieInfo?,
    var movieDetails: MovieDetails?,
    var tmdbMovieResult: MovieResult?
)
