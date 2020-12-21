package com.dicoding.helfani.mysubmissionfinal.adapter

import com.dicoding.helfani.mysubmissionfinal.entity.UserItems
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.helfani.mysubmissionfinal.R
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    internal var mData = arrayListOf<UserItems>()

    fun setData(items: ArrayList<UserItems>) {
     if (mData.size > 0) {
            this.mData.clear()
      }
      this.mData.addAll(items)
      notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserViewHolder {
        val view:View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.bind(mData[position])
        userViewHolder.itemView.setOnClickListener {onItemClickCallback.onItemClicked(mData[position])}
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
        fun bind(user : UserItems){
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