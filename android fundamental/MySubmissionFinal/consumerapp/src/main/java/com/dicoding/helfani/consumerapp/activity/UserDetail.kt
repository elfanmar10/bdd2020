package com.dicoding.helfani.consumerapp.activity

import com.dicoding.helfani.consumerapp.entity.UserItems
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.helfani.consumerapp.R
import com.dicoding.helfani.consumerapp.adapter.SectionsPagerAdapter
import com.dicoding.helfani.consumerapp.db.DatabaseContract
import com.dicoding.helfani.consumerapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.helfani.consumerapp.helper.MappingHelper
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.item_user.img_avatar
import kotlinx.android.synthetic.main.item_user.tv_username
import org.json.JSONObject


class UserDetail : AppCompatActivity() {

    private var user: UserItems? = null
    private var statusFavoriteUser = false

    val detailUser = ArrayList<UserItems>()

    private lateinit var uriWithid: Uri

    companion object {
        val TAG = UserDetail::class.java.simpleName
        const val EXTRA_USER = "extra_user"
        const val GITHUB_TOKEN = "BuildConfig.GITHUB_TOKEN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val actionBar = supportActionBar

		supportActionBar?.title = "Detail User"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        var user = intent.getParcelableExtra<UserItems>(EXTRA_USER)

        Glide.with(this)
            .load(user?.avatar)
            .apply(RequestOptions())
            .into(img_avatar)


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = user?.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f

        // Uri yang di dapatkan disini akan digunakan untuk ambil data dari provider
        // content://com.dicoding.helfani.mysubmissionfinal/favorite/id

        uriWithid = Uri.parse(CONTENT_URI.toString() + "/" + user?.id)

        val isFav = contentResolver.query(uriWithid, null, null, null, null)

        if (isFav != null) {
            user = MappingHelper.mapCursorToObject(isFav)
            isFav.close()
            statusFavoriteUser = true
            setStatusFavorite(statusFavoriteUser)
        }
        getDetailUser(user?.username)

    }

    private fun getDetailUser(username: String?) {

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
                    Log.d(TAG, result)

                    val item = JSONObject(result)

                    val userItems = UserItems()
                    userItems.id = item.getString("id")
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

                    fab_fav.setOnClickListener {

                        if (!statusFavoriteUser) {
                            val intent = Intent()
                            intent.putExtra(EXTRA_USER, user)

                            val values = ContentValues()
                            values.put(DatabaseContract.UserColumns._ID, userItems.id)
                            values.put(DatabaseContract.UserColumns.AVATAR, userItems.avatar)
                            values.put(DatabaseContract.UserColumns.USERNAME, userItems.username)
                            values.put(DatabaseContract.UserColumns.NAME, userItems.name)
                            values.put(DatabaseContract.UserColumns.COMPANY, userItems.company)
                            values.put(DatabaseContract.UserColumns.LOCATION, userItems.location)
                            values.put(DatabaseContract.UserColumns.FOLLOWERS, userItems.followers)
                            values.put(DatabaseContract.UserColumns.FOLLOWING, userItems.following)
                            values.put(
                                DatabaseContract.UserColumns.REPOSITORY,
                                userItems.repository
                            )
                            // Gunakan content uri untuk insert
                            //  content://com.dicoding.helfani.mysubmissionfinal/favorite
                            contentResolver.insert(CONTENT_URI, values)

                            statusFavoriteUser = !statusFavoriteUser

                            setStatusFavorite(statusFavoriteUser)
                            Toast.makeText(
                                this@UserDetail,
                                "User ditambahkan ke favorit",
                                Toast.LENGTH_SHORT
                            ).show()


                        } else {

                            contentResolver.delete(uriWithid, null, null)
                            statusFavoriteUser = !statusFavoriteUser
                            setStatusFavorite(statusFavoriteUser)
                            Toast.makeText(
                                this@UserDetail,
                                "User dihapus dari favorit",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

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

    private fun setStatusFavorite(statusFavoriteUser: Boolean) {
        if (statusFavoriteUser)
            fab_fav.setImageResource(R.drawable.ic_favorite_white)
        else
            fab_fav.setImageResource(R.drawable.ic_favorite_border_white)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
