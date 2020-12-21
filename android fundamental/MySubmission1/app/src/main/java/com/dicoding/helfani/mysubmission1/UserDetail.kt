package com.dicoding.helfani.mysubmission1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.item_user.img_avatar
import kotlinx.android.synthetic.main.item_user.tv_name
import kotlinx.android.synthetic.main.item_user.tv_username
import layout.User

class UserDetail : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions())
            .into(img_avatar)

        tv_username.text = user.username.toString()
        tv_name.text = user.name.toString()
        tv_followers.text = user.followers.toString()
        tv_following.text = user.following.toString()
        tv_company.text = user.company.toString()
        tv_location.text = user.location.toString()
        tv_repository.text = user.repository.toString()


        val actionBar = supportActionBar
        actionBar!!.title = "Detail User"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}