package com.dicoding.helfani.mymovielist.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val favSectionPagerAdapter = FavoriteSectionsPagerAdapter(this, supportFragmentManager)
        activityFavoriteBinding.viewPager.adapter = favSectionPagerAdapter
        activityFavoriteBinding.tabs.setupWithViewPager(activityFavoriteBinding.viewPager)

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.favorite)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}