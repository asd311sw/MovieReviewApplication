package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.reserve.ReserveAdapter
import com.example.myapplication.database.ReserveMovieDatabase
import com.example.myapplication.databinding.FragmentReserveBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReserveFragment:Fragment() {

    private val binding:FragmentReserveBinding by lazy {
        FragmentReserveBinding.inflate(layoutInflater)
    }

    private val reserveAdapter:ReserveAdapter by lazy {
        ReserveAdapter()
    }

    private val reserverMovieDatabase by lazy {
        ReserveMovieDatabase.getInstance(requireContext())
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
            adapter = reserveAdapter
        }

        getMovieList()
    }

    private fun bindView(){
        binding.refreshLayout.setOnRefreshListener {
            reserveAdapter.items.clear()
            reserveAdapter.notifyDataSetChanged()
            getMovieList()
        }


    }

    private fun getMovieList(){
        binding.refreshLayout.isRefreshing = true

        CoroutineScope(Dispatchers.IO).launch {

            val movieList = reserverMovieDatabase?.reserveMovieDao()?.getAll()
            CoroutineScope(Dispatchers.Main).launch {

                movieList?.let {
                    for (i in 0 until it.size)
                        reserveAdapter.items.add(it[i])
                }

                reserveAdapter.notifyDataSetChanged()
            }

        }


        binding.refreshLayout.isRefreshing = false


    }


}