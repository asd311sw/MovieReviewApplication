package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemMovieBinding

class MovieAdapter:RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    val items = ArrayList<MovieReview>()

    inner class ViewHolder(val binding:ItemMovieBinding):RecyclerView.ViewHolder(binding.root){

        init {


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            idTextView.text = items[position].id
            timeTextView.text = items[position].postTime
            ratingBar.rating = items[position].rating.toFloat()
            contentTextView.text = items[position].content
            goodTextView.text = items[position].good
        }

    }

    override fun getItemCount() = items.size

}