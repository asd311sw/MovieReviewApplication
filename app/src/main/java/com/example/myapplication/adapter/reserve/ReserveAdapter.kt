package com.example.myapplication.adapter.reserve

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.database.ReserveMovieDatabase
import com.example.myapplication.database.entity.ReserveMovie
import com.example.myapplication.databinding.ItemFavoriteBinding
import com.example.myapplication.databinding.ItemReserveBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class ReserveAdapter : RecyclerView.Adapter<ReserveAdapter.ViewHolder>() {
    val items = ArrayList<ReserveMovie>()


    inner class ViewHolder(val binding: ItemReserveBinding) :
        RecyclerView.ViewHolder(binding.root) {


        private val database by lazy {
            ReserveMovieDatabase?.getInstance(binding.root.context)
        }

        init {
            binding.deleteButton.setOnClickListener {

                val position = adapterPosition

                CoroutineScope(Dispatchers.IO).launch {
                    database?.reserveMovieDao()?.deleteMovie(items[position].movieName)
                    items.removeAt(position)

                }

                notifyDataSetChanged()
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = ItemReserveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            setMoviePoster(items[position].posterPath, this)
            textView.text = items[position].movieName
            movieInfoTextView.text = items[position].movieInfo
        }

    }

    override fun getItemCount() = items.size


    private fun setMoviePoster(posterPath: String?,binding: ItemReserveBinding){
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