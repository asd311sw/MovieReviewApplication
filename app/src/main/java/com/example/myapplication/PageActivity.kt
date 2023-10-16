package com.example.myapplication

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.ActivityPageBinding
import me.relex.circleindicator.CircleIndicator3


class PageActivity:FragmentActivity() {

    private val binding:ActivityPageBinding by lazy {
        ActivityPageBinding.inflate(layoutInflater)
    }
    private val pageAdapter:PagerAdapter by lazy {
        PagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        bindView()
    }

    private fun initView(){
        binding.viewPager.apply {
            adapter = pageAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
        }


        binding.circleIndicator.setViewPager(binding.viewPager)
        pageAdapter.registerAdapterDataObserver(binding.circleIndicator.adapterDataObserver)
    }

    private fun bindView(){
        binding.viewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position){
                    0 -> Toast.makeText(this@PageActivity,"영화목록 페이지입니다.",Toast.LENGTH_LONG).show()
                    1 -> Toast.makeText(this@PageActivity,"베스트 영화 페이지입니다.",Toast.LENGTH_LONG).show()
                    2 -> Toast.makeText(this@PageActivity,"프로필 페이지입니다.",Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.firstButton.setOnClickListener {
            binding.viewPager.currentItem = 0
        }

        binding.secondButton.setOnClickListener {
            binding.viewPager.currentItem = 1
        }

        binding.thirdButton.setOnClickListener {
            binding.viewPager.currentItem = 2
        }
    }


    inner class PagerAdapter:FragmentStateAdapter {

        constructor(activity:FragmentActivity):super(activity)

        override fun getItemCount() = 3

        override fun createFragment(position: Int) = when(position){
                0 -> MenuFragment()
                1 -> ReviewFragment()
                2 -> LoginFragment()
                else -> MenuFragment()
        }

    }
}