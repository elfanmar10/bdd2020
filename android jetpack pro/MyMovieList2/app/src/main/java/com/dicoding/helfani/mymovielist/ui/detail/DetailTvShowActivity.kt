package com.dicoding.helfani.mymovielist.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.databinding.ActivityDetailTvShowBinding
import com.dicoding.helfani.mymovielist.databinding.ContentDetailTvshowBinding
import com.dicoding.helfani.mymovielist.viewmodel.ViewModelFactory

class DetailTvShowActivity : AppCompatActivity() {

    private lateinit var detailTvshowBinding: ContentDetailTvshowBinding

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailTvShowBinding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        detailTvshowBinding = activityDetailTvShowBinding.detailContent

        setContentView(activityDetailTvShowBinding.root)

        setSupportActionBar(activityDetailTvShowBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val tvShowId = extras.getString(EXTRA_TVSHOW)
            if (tvShowId != null) {

                activityDetailTvShowBinding.progressBar.visibility = View.VISIBLE

                viewModel.setSelectedTvShow(tvShowId)

                viewModel.getTvShow().observe(this,  { tvshow ->
                    activityDetailTvShowBinding.progressBar.visibility = View.GONE
                    activityDetailTvShowBinding.content.visibility = View.VISIBLE
                    populateTvShow(tvshow)})
            }
        }
    }

    private fun populateTvShow(tvShowEntity: TvShowEntity) {
        detailTvshowBinding.tvTitle.text = tvShowEntity.title
        detailTvshowBinding.tvGenre.text = tvShowEntity.genre
        detailTvshowBinding.tvCreator.text = tvShowEntity.creator
        detailTvshowBinding.tvOverview.text = tvShowEntity.overview

        Glide.with(this)
            .load(tvShowEntity.imagePath)
            .transform(RoundedCorners(20))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_error)
            .into(detailTvshowBinding.imagePoster)
    }
}