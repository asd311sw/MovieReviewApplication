package com.example.myapplication.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.databinding.ActivityVolleyBinding
import com.example.myapplication.dto.MovieData
import com.example.myapplication.dto.MovieList
import com.example.myapplication.dto.detail.MovieDetails
import com.example.myapplication.dto.detail.MovieInfoDetails
import com.example.myapplication.dto.tmdb.TmdbMovieDetails
import com.google.gson.Gson

class VolleyActivity:AppCompatActivity() {

    companion object{
        var requestQueue:RequestQueue? = null
    }

    private val binding:ActivityVolleyBinding by lazy {
        ActivityVolleyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        initView()
        bindView()

    }

    private fun sendRequest(){


        val apiKey = "0611d02125e66ae6edc1f53100f20fe9"
        val url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=$apiKey&targetDt=20230908"

        val request = object:StringRequest(
            Request.Method.GET,
            url,
            {
                if (it != null) {
                    processResponse(it)

                }
            },
            {
                if (it != null) {
                    binding.requestTextView.text = it.message
                }
            }

        ){

            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String,String>()

                return params
            }

        }


        request.setShouldCache(false)
        requestQueue = Volley.newRequestQueue(this)
        requestQueue!!.add(request)

    }

    private fun processResponse(response: String){
        val gson = Gson()
        val boxOffice = gson.fromJson(response,BoxOffice::class.java)
        binding.requestTextView.text = boxOffice.boxOfficeResult.dailyBoxOfficeList.size.toString()


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
            requestQueue = Volley.newRequestQueue(this)
            requestQueue!!.add(request)

        }


    }

    private fun processDetailsResponse(response: String,index:Int){
        val gson = Gson()

        val movieInfoDetails = gson.fromJson(response,MovieInfoDetails::class.java)
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
        requestQueue = Volley.newRequestQueue(this)
        requestQueue!!.add(request)
    }

    private fun processTMDBSearchResponse(response: String,index: Int){

        val gson = Gson()
        val tmdbMovieDetails = gson.fromJson(response,TmdbMovieDetails::class.java)

        val movieResult = tmdbMovieDetails.results[0]

        MovieList.data[index].tmdbMovieResult = movieResult

        setPosterImage(index,movieResult.poster_path)
    }

    private fun setPosterImage(index:Int,posterPath:String?){


        posterPath?.let {
            val url = "https://image.tmdb.org/t/p/w200$posterPath"

            var targetImageView:ImageView

            targetImageView = when(index){
                0 -> binding.poster1
                1 -> binding.poster2
                2 -> binding.poster3
                3 -> binding.poster4
                4 -> binding.poster5
                else -> binding.poster1
            }

            Glide.with(this)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(targetImageView)




        }

    }

    private fun initView(){

        requestQueue = Volley.newRequestQueue(this)

    }

    private fun bindView(){
        binding.requestButton.setOnClickListener {
            sendRequest()
        }
    }
}