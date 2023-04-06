package com.example.mysubmissionawal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysubmissionawal.R
import com.example.mysubmissionawal.model.Follow

class FollAdapter(private val listUser: List<Follow>) :
    RecyclerView.Adapter<FollAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_user_list, viewGroup, false)
        )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemUser: TextView = view.findViewById(R.id.tv_item_user)
        val tvImg: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val imgUrl = listUser[position].avatarUrl
        val login = listUser[position].login
        viewHolder.tvItemUser.text = login
        Glide.with(viewHolder.itemView.context)
            .load(imgUrl)
            .into(viewHolder.tvImg)
    }

    override fun getItemCount() = listUser.size
}