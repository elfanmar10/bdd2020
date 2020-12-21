package com.dicoding.helfani.mysubmission1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_user.view.img_avatar
import kotlinx.android.synthetic.main.item_user.view.tv_name
import kotlinx.android.synthetic.main.item_user.view.tv_username
import layout.User

class UserAdapter internal constructor(private val context: Context): BaseAdapter(){
    internal var usernames = arrayListOf<User>()

    override fun getItem(i: Int): Any = usernames[i]

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getCount(): Int = usernames.size

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var itemView = view
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val username = getItem(position) as User
        viewHolder.bind(username)
        return itemView
    }

    inner class ViewHolder constructor(private val view:View) {
        fun bind(user: User){
            with(view) {
                tv_username.text = user.username
                tv_name.text = user.name
                img_avatar.setImageResource(user.avatar)

            }
        }
    }
}