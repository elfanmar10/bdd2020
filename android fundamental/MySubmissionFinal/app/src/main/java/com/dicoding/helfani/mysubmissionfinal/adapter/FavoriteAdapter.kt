package com.dicoding.helfani.mysubmissionfinal.adapter

import com.dicoding.helfani.mysubmissionfinal.entity.UserItems
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.helfani.mysubmissionfinal.R
import kotlinx.android.synthetic.main.item_user.view.*

class FavoriteAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteAdapter.UserViewHolder>() {

    internal var listUser = arrayListOf<UserItems>()
        set(listUser) {
            if (listUser.size > 0) {
                this.listUser.clear()
            }
            this.listUser.addAll(listUser)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(userViewholder: UserViewHolder, position: Int) {
        userViewholder.bind(listUser[position])
        userViewholder.itemView.setOnClickListener {onItemClickCallback.onItemClicked(listUser[position])}
    }

    override fun getItemCount(): Int = this.listUser.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserItems) {
            with(itemView) {
                tv_username.text = user.username

                Glide.with(this)
                    .load(user.avatar)
                    .into(img_avatar)

            }
        }

    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItems)

    }

}