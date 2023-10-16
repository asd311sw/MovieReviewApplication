package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.adapter.MovieAdapter
import com.example.myapplication.adapter.MovieReview
import com.example.myapplication.database.FavoriteMovieDatabase
import com.example.myapplication.database.ReserveMovieDatabase
import com.example.myapplication.database.entity.FavoriteMovie
import com.example.myapplication.database.entity.ReserveMovie
import com.example.myapplication.databinding.FragmentReviewBinding
import com.example.myapplication.dto.MovieList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewFragment : Fragment() {

    private val binding: FragmentReviewBinding by lazy {
        FragmentReviewBinding.inflate(layoutInflater)
    }


    private val bundle: Bundle by lazy {
        Bundle()
    }
//
//    private val movieAdapter: MovieAdapter by lazy {
//        MovieAdapter()
//    }

    private val favoriteMovieDatabase by lazy {
        FavoriteMovieDatabase.getInstance(requireContext())
    }

    private val reserverMovieDatabase by lazy {
        ReserveMovieDatabase.getInstance(requireContext())
    }

    private var isChecked = false
    private val position = MovieList.position
    private val movieData = MovieList.data[position]
    private val peopleName = movieData.movieDetails?.actors?.get(0)?.peopleNm
    private val showTime = movieData.movieDetails?.showTm
    private val openDate = movieData.movieInfo?.openDt
    private val genreName = movieData.movieDetails?.genres?.get(0)?.genreNm
    private val movieWatchGrade =
        MovieList.data[position].movieDetails?.audits?.get(0)?.watchGradeNm
    private val movieInfo = movieData.movieInfo?.audiCnt + "명 | " + movieWatchGrade
    private var moviePosterPath: String = ""
    private var movieName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        bindView()

    }

    fun initInfo(moviePosterPath: String?, movieName: String?, movieInfo: String?) {

        bundle.apply {
            putString("moviePosterPath", moviePosterPath)
            putString("movieName", movieName)
            putString("movieInfo", movieInfo)
            this@ReviewFragment.movieName = bundle.getString("movieName")!!
            this@ReviewFragment.moviePosterPath = getString("moviePosterPath")!!
        }


    }


    private fun initView() {

        CoroutineScope(Dispatchers.IO).launch {
            val favoriteMovie =
                favoriteMovieDatabase?.favoriteMovieDao()?.getFavoriteMovie(movieName)

            favoriteMovie?.let {
                binding.ratingButton.setBackgroundResource(R.drawable.checked_star_24)
            }


        }



        binding.apply {
            infoTextView.text =
                openDate + "개봉\n" + genreName + "/" + showTime + "분"
            popularityNumberTextView.text =
                MovieList.data[position].movieDetails?.audits?.get(0)?.watchGradeNm
            ratingNumberTextView.text = movieData.movieDetails?.nations?.get(0)?.nationNm
            audienceNumberTextView.text = movieData.movieInfo?.audiCnt
            supervisorNameTextView.text = movieData.movieDetails?.directors?.get(0)?.peopleNm
            actorNameTextView.text =
                peopleName + "(" + movieData.movieDetails?.actors?.get(
                    0
                )?.cast + ")"
            companyNameTextView.text = movieData.movieDetails?.companys?.get(0)?.companyNm
            storyTextView.text = movieData.tmdbMovieResult?.overview
        }

        bundle.apply {

            moviePosterPath = getString("moviePosterPath")!!
            movieName = getString("movieName")!!
            setPosterImage(moviePosterPath)
            binding.nameTextView.text = movieName
        }
//
//        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//            adapter = movieAdapter
//        }

//        movieAdapter.items.add(MovieReview("why*****", "1분전", 5, "강추!", "추천 5"))
//        movieAdapter.notifyDataSetChanged()


    }

    private fun setPosterImage(posterPath: String?) {


        posterPath?.let {
            val url = "https://image.tmdb.org/t/p/w200$posterPath"

            Glide.with(this)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontAnimate()
                .into(binding.imageView)
        }

    }

    private fun bindView() {

        binding.ratingButton.setOnClickListener {

            if (isChecked)
                deleteFavoriteMovie()
            else
                insertFavoriteMovie()
        }
//
//        binding.writeButton.setOnClickListener {
//
//        }
//
//        binding.entireButton.setOnClickListener {
//
//
//        }

        binding.reserveButton.setOnClickListener {

            insertReserveMovie()

        }

    }


    private fun insertFavoriteMovie() {
        binding.ratingButton.setBackgroundResource(R.drawable.checked_star_24)


        CoroutineScope(Dispatchers.IO).launch {
            favoriteMovieDatabase?.favoriteMovieDao()?.insertFavoriteMovie(
                    posterPath = moviePosterPath,
                    movieName = movieName,
                    movieInfo = movieInfo
            )
        }

        isChecked = true
    }

    private fun deleteFavoriteMovie() {

        binding.ratingButton.setBackgroundResource(R.drawable.baseline_star_border_24)

        CoroutineScope(Dispatchers.IO).launch {
            favoriteMovieDatabase?.favoriteMovieDao()?.deleteMovie(movieName)

        }

        isChecked = false
    }


    private fun insertReserveMovie() {
        CoroutineScope(Dispatchers.IO).launch {

            val reserveMovie = reserverMovieDatabase?.reserveMovieDao()?.getReserveMovie(movieName)


            if (reserveMovie == null) {
                reserverMovieDatabase?.reserveMovieDao()?.insertMovie(
                        posterPath = moviePosterPath,
                        movieName = movieName,
                        movieInfo = movieInfo
                )
            } else {

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "이미 예약된 영화압니다.", Toast.LENGTH_SHORT).show()
                }

            }


        }

    }


}
