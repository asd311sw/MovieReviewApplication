package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.activity.BoxOffice
import com.example.myapplication.activity.MovieInfo
import com.example.myapplication.activity.VolleyActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.dto.MovieData
import com.example.myapplication.dto.MovieList
import com.example.myapplication.dto.detail.MovieDetails
import com.example.myapplication.dto.detail.MovieInfoDetails
import com.example.myapplication.dto.tmdb.TmdbMovieDetails
import com.example.myapplication.fragment.FavoriteFragment
import com.example.myapplication.fragment.ReserveFragment
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers


class MainActivity : AppCompatActivity() {
    companion object {
        var requestQueue: RequestQueue? = null
    }



    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewAdapter: ViewAdapter by lazy {
        ViewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        bindView()

    }


    private fun initView() {


        setSupportActionBar(binding.toolBar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolBar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        toggle.drawerArrowDrawable.color = getColor(R.color.white)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        requestQueue = Volley.newRequestQueue(this)

    }


    private fun bindView() {
        sendRequest()


        binding.navigationView.setNavigationItemSelectedListener {


            when (it.itemId) {
                R.id.movieList -> {


                    changeFragment(MenuFragment())

                    binding.collapsingToolbarLayout.title = "영화 목록"

                }

                R.id.preferedMovie -> {
                    changeFragment(FavoriteFragment())
                    binding.collapsingToolbarLayout.title = "즐겨찾기"

                }

                R.id.bookedMovie -> {
                    changeFragment(ReserveFragment())
                    binding.collapsingToolbarLayout.title = "예매 목록"
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true

        }

    }


    private fun sendRequest(){

        val apiKey = "0611d02125e66ae6edc1f53100f20fe9"
        val url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=$apiKey&targetDt=20230908"

        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                if (it != null) {
                    processResponse(it)

                }
            },
            {
                if (it != null) {
                    //binding.requestTextView.text = it.message
                }
            }

        ){

            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String,String>()

                return params
            }

        }


        request.setShouldCache(false)
        VolleyActivity.requestQueue = Volley.newRequestQueue(this)
        VolleyActivity.requestQueue!!.add(request)

    }


    private fun processResponse(response: String){
        val gson = Gson()
        val boxOffice = gson.fromJson(response, BoxOffice::class.java)
        //binding.requestTextView.text = boxOffice.boxOfficeResult.dailyBoxOfficeList.size.toString()


        requestDetails(boxOffice.boxOfficeResult.dailyBoxOfficeList)
    }

    private fun requestDetails(dailyBoxOfficeList:ArrayList<MovieInfo>){


        for(index in 0..4){

            val movieData = MovieData(dailyBoxOfficeList[index],null,null)

            MovieList.data.add(movieData)
            val movieCd = dailyBoxOfficeList[index].movieCd


            sendDetails(index,movieCd)
        }

    }

    private fun sendDetails(index:Int,movieCd:String?){

        movieCd?.let {
            val apiKey = "0611d02125e66ae6edc1f53100f20fe9"
            val url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=$apiKey&movieCd=$movieCd"

            val request = object :StringRequest(
                Request.Method.GET,
                url,
                {
                    it?.let {

                        processDetailsResponse(it,index)
                    }
                },
                {
                    it?.let {

                    }

                }
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap < String, String>()

                    return params
                }
            }


            request.setShouldCache(false)
            VolleyActivity.requestQueue = Volley.newRequestQueue(this)
            VolleyActivity.requestQueue!!.add(request)

        }


    }

    private fun processDetailsResponse(response: String,index:Int){
        val gson = Gson()

        val movieInfoDetails = gson.fromJson(response, MovieInfoDetails::class.java)
        val movieDetails = movieInfoDetails.movieInfoResult?.movieInfo

        MovieList.data[index].movieDetails = movieDetails

        requestTMDBSearch(index,movieDetails)


    }

    private fun requestTMDBSearch(index: Int,movieDetails: MovieDetails?){

        val movieName = movieDetails?.movieNm

        sendTMDBSearch(index,movieName)

    }

    private fun sendTMDBSearch(index:Int,movieName:String?){
        val apiKey = "a679796b074e5618f08e2e3d06f1d1d6"
        val url = "https://api.themoviedb.org/3/search/movie?query=$movieName&language=ko-KR&page=1&api_key=$apiKey"

        val request = object :StringRequest(
            Request.Method.GET,
            url,
            {
                it?.let {

                    processTMDBSearchResponse(it,index)

                }
            },
            {
                it?.let {

                }
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String,String>()

                return params
            }
        }

        request.setShouldCache(false)
        VolleyActivity.requestQueue = Volley.newRequestQueue(this)
        VolleyActivity.requestQueue!!.add(request)
    }

    private fun processTMDBSearchResponse(response: String,index: Int){

        val gson = Gson()
        val tmdbMovieDetails = gson.fromJson(response, TmdbMovieDetails::class.java)

        val movieResult = tmdbMovieDetails.results[0]

        MovieList.data[index].tmdbMovieResult = movieResult

      //  setPosterImage(index,movieResult.poster_path)
    }



    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()


    }


    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()

    }


}