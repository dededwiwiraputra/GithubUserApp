package com.example.mysubmissionawal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserListAdapter(private val listUser: ArrayList<UserModel>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_user_list, viewGroup, false))

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemUser: TextView = view.findViewById(R.id.tv_item_user)
        val tvImg: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (name, idUser, imgUrl) = listUser[position]
        viewHolder.tvItemUser.text = name
        Glide.with(viewHolder.itemView.context)
            .load(imgUrl)
            .into(viewHolder.tvImg)
    }

    override fun getItemCount() = listUser.size
}