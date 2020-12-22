package com.dicoding.helfani.mysubmission2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.img_avatar
import kotlinx.android.synthetic.main.item_user.tv_username
import UserItems
import android.util.Log
import android.view.View
import com.dicoding.helfani.mysubmission2.MainActivity.Companion.GITHUB_TOKEN
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.json.JSONObject
import java.lang.Exception

class UserDetail : AppCompatActivity() {

    companion object {
        val TAG = UserDetail::class.java.simpleName
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val actionBar = supportActionBar

		supportActionBar?.title = "Detail User"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        val user = intent.getParcelableExtra<UserItems>(EXTRA_USER) as UserItems

        getDetailUser(user.username)

        Glide.with(this)
            .load(user.avatar)
            .apply(RequestOptions())
            .into(img_avatar)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = user?.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f

    }

    private fun getDetailUser(username: String?) {

        val detailUser = ArrayList<UserItems>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", GITHUB_TOKEN)
        client.addHeader("User-Agent", "request")

        progressBarDetail.visibility = View.VISIBLE

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                //Jika koneksi berhasil
                progressBarDetail.visibility = View.INVISIBLE

                try {
                    //parsing json
                    val result = String(responseBody)
                    Log.d(TAG,result)

                    val item = JSONObject(result)

                    val userItems = UserItems()
                    userItems.avatar = item.getString("avatar_url")
                    userItems.username = item.getString("login")
                    userItems.name = item.getString("name")
                    userItems.location = item.getString("location")
                    userItems.company = item.getString("company")
                    userItems.repository = item.getString("public_repos")
                    userItems.followers = item.getString("followers")
                    userItems.following = item.getString("following")

                    detailUser.add(userItems)

                    tv_username.text = userItems.username.toString()
                    tv_name.text = userItems.name.toString()
                    tv_location.text = userItems.location.toString()
                    tv_company.text = userItems.company.toString()
                    tv_repository.text = userItems.repository.toString()
                    tv_follower.text = userItems.followers.toString()
                    tv_following.text = userItems.following.toString()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                //Jika koneksi gagal
                progressBarDetail.visibility = View.INVISIBLE
                Log.d("OnFailure", error.message.toString())
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
