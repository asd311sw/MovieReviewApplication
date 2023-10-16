package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentMenuBinding
import com.example.myapplication.dto.MovieData
import com.example.myapplication.dto.MovieList
import com.example.myapplication.fragment.MovieFragment

class MenuFragment:Fragment() {

    private val binding:FragmentMenuBinding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }


    private val pageAdapter: PageAdapter by lazy {
        PageAdapter(requireActivity().supportFragmentManager,requireActivity().lifecycle)
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



        initView()
        Log.d("MAINACTIVITY","CHECK_OUT:"+MovieList.data.size)

        bindView()

    }

    private fun initView(){

        binding.viewPager.apply {
            adapter = pageAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 5
        }

        binding.circleIndicator.setViewPager(binding.viewPager)
        pageAdapter.registerAdapterDataObserver(binding.circleIndicator.adapterDataObserver)
    }

    private fun bindView(){


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                MovieList.position = position
                //Toast.makeText(requireContext(),"$position 선택됨",Toast.LENGTH_SHORT).show()
            }
        })



    }


    inner class PageAdapter:FragmentStateAdapter{

        constructor(fm:FragmentManager,lc:Lifecycle):super(fm,lc)

        override fun getItemCount() = MovieList.data.size

        override fun createFragment(position: Int): Fragment {
            MovieList.position = position

            val movieFragment = MovieFragment()

            val movieName = MovieList.data[position].movieDetails?.movieNm
            val moviePosterPath = MovieList.data[position].tmdbMovieResult?.poster_path
            val movieAuditCnt = MovieList.data[position].movieInfo?.audiCnt
            val movieWatchGrade = MovieList.data[position].movieDetails?.audits?.get(0)?.watchGradeNm


            movieFragment.initView(moviePosterPath,movieName,movieAuditCnt,movieWatchGrade)

            return movieFragment

        }

    }

}