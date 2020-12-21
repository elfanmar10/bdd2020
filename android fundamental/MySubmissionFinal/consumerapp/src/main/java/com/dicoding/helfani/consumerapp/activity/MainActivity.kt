package com.dicoding.helfani.consumerapp.activity

import com.dicoding.helfani.consumerapp.entity.UserItems
import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.helfani.consumerapp.R
import com.dicoding.helfani.consumerapp.adapter.FavoriteAdapter
import com.dicoding.helfani.consumerapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.helfani.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle(R.string.list_favorite_users)

        adapter = FavoriteAdapter(this)
        adapter.notifyDataSetChanged()
        rvFavorite.layoutManager = LinearLayoutManager(this)
        rvFavorite.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                showSelectedUser(data)
            }
        })


        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavAsync()
            }
        }

        contentResolver.registerContentObserver(
          CONTENT_URI,
            true,
            myObserver
        )

        if (savedInstanceState == null) {
            loadFavAsync()
        } else {
            savedInstanceState.getParcelableArrayList<UserItems>(EXTRA_STATE)
                ?.also { adapter.listUser = it }
        }
    }

    private fun loadFavAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBarFavorite.visibility = View.VISIBLE
            val deferredUsers = async(Dispatchers.IO) {
                // CONTENT_URI = content://com.dicoding.helfani.mysubmissionfinal/favorite
                val cursor = contentResolver.query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressBarFavorite.visibility = View.INVISIBLE
            val users = deferredUsers.await()
            if (users.size > 0) {
                adapter.listUser = users
            } else {
                adapter.listUser = ArrayList()
                Toast.makeText(this@MainActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listUser)
    }


    private fun showSelectedUser(data: UserItems) {
        val detailIntent = Intent(this@MainActivity, UserDetail::class.java)
        detailIntent.putExtra(UserDetail.EXTRA_USER, data)
        startActivity(detailIntent)
    }

}