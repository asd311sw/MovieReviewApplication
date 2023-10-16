package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemViewBinding

class ViewAdapter:RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

    val items = ArrayList<Profile>()

    inner class ViewHolder(val binding: ItemViewBinding):RecyclerView.ViewHolder(binding.root){
        init {


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAdapter.ViewHolder{
        val view = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            imageView.setImageResource(R.drawable.business_man)
            nameTextView.text = items[position].name
            phoneTextView.text = items[position].phoneNumber
        }

    }


    override fun getItemCount() = items.size
}