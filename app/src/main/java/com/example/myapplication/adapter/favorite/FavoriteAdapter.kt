package com.example.myapplication.adapter.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.database.entity.FavoriteMovie
import com.example.myapplication.databinding.ItemFavoriteBinding

class FavoriteAdapter:RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    val items = ArrayList<FavoriteMovie>()

    inner class ViewHolder(val binding:ItemFavoriteBinding):RecyclerView.ViewHolder(binding.root){

        init {



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            setMoviePoster(items[position].posterPath,this)
            movieNameTextView.text = items[position].movieName
            movieInfoTextView.text = items[position].movieInfo
        }


    }

    override fun getItemCount() = items.size


    private fun setMoviePoster(posterPath: String?,binding: ItemFavoriteBinding){
        posterPath?.let {
            val url = "https://image.tmdb.org/t/p/w200$posterPath"

            Glide.with(binding.root)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontAnimate()
                .into(binding.imageView)
        }


    }



}