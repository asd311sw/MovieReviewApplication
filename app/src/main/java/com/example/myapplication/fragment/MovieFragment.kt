package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.ReviewFragment
import com.example.myapplication.databinding.FragmentMovieBinding
import com.example.myapplication.dto.MovieList


class MovieFragment : Fragment() {

    private val binding: FragmentMovieBinding by lazy {
        FragmentMovieBinding.inflate(layoutInflater)
    }

    private val bundle: Bundle by lazy {
        Bundle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        bundle.apply {
            setPosterImage(getString("moviePosterPath"))
            binding.nameTextView.text = getString("movieName")
            binding.infoTextView.text = getString("movieAuditCnt")+"명 | "+getString("movieWatchGrade")
        }

        binding.detailButton.setOnClickListener {

            val reviewFragment = ReviewFragment()

            reviewFragment.initInfo(
                bundle.getString("moviePosterPath"),
                bundle.getString("movieName"),
                bundle.getString("movieAuditCnt")

            )



            changeFragment(reviewFragment)
        }



    }


    fun initView(moviePosterPath: String?, movieName: String?, movieAuditCnt:String?,movieWatchGrade:String?) {

        bundle.apply {
            putString("moviePosterPath", moviePosterPath)
            putString("movieName", movieName)
            putString("movieAuditCnt", movieAuditCnt)
            putString("movieWatchGrade",movieWatchGrade)

        }
    }

    private fun setPosterImage(posterPath:String?){


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


    private fun changeFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

}