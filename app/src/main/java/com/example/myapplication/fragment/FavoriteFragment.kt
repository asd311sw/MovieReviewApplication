package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.favorite.FavoriteAdapter
import com.example.myapplication.database.FavoriteMovieDatabase
import com.example.myapplication.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment:Fragment() {
    private val binding:FragmentFavoriteBinding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }

    private val favoriteAdapter:FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

    private val favoriteMovieDatabase by lazy {
        FavoriteMovieDatabase.getInstance(requireContext())
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
        bindView()
    }


    private fun initView(){

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            adapter = favoriteAdapter
        }

        getMovieList()
    }


    private fun bindView(){


        binding.refreshLayout.setOnRefreshListener {
            favoriteAdapter.items.clear()
            favoriteAdapter.notifyDataSetChanged()
            getMovieList()
        }

    }



    private fun getMovieList(){
        binding.refreshLayout.isRefreshing = true

        CoroutineScope(Dispatchers.IO).launch {

            val movieList = favoriteMovieDatabase?.favoriteMovieDao()?.getAll()
            CoroutineScope(Dispatchers.Main).launch {

                movieList?.let {
                    for (i in 0 until it.size)
                        favoriteAdapter.items.add(it[i])
                }

                favoriteAdapter.notifyDataSetChanged()
            }

        }


        binding.refreshLayout.isRefreshing = false


    }

}