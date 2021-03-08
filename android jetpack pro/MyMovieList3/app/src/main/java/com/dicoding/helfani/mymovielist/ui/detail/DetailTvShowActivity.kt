package com.dicoding.helfani.mymovielist.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.databinding.ActivityDetailTvShowBinding
import com.dicoding.helfani.mymovielist.viewmodel.ViewModelFactory
import com.dicoding.helfani.mymovielist.vo.Status
import kotlinx.android.synthetic.main.content_detail_tvshow.fab_fav

class DetailTvShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    private var _activityDetailTvShowBinding: ActivityDetailTvShowBinding? = null

    private val mainBinding get() = _activityDetailTvShowBinding
    private val contentBinding get() = _activityDetailTvShowBinding?.detailContent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailTvShowBinding = ActivityDetailTvShowBinding.inflate(layoutInflater)

        setContentView(mainBinding?.root)

        setSupportActionBar(mainBinding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailTvShowViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val tvShowId = extras.getString(EXTRA_TVSHOW)
            if (tvShowId != null) {
                viewModel.setSelectedTvShow(tvShowId)

                viewModel.tvShow.observe(this, { tvshowResource ->
                    if (tvshowResource != null) {
                        when (tvshowResource.status) {
                            Status.LOADING -> mainBinding?.progressBar?.visibility = View.VISIBLE
                            Status.SUCCESS -> if (tvshowResource.data != null) {
                                mainBinding?.progressBar?.visibility = View.GONE
                                mainBinding?.content?.visibility = View.VISIBLE
                                val state = tvshowResource.data.favorited
                                setFavoriteState(state)

                                populateTvShow(tvshowResource.data)
                            }
                            Status.ERROR -> {
                                mainBinding?.progressBar?.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    "Terjadi kesalahan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                })
            }
        }
        fab_fav.setOnClickListener {
            viewModel.setFavorite()
        }
    }

    private fun populateTvShow(tvShowEntity: TvShowEntity) {
        contentBinding?.tvTitle?.text = tvShowEntity.title
        contentBinding?.tvGenre?.text = tvShowEntity.genre
        contentBinding?.tvCreator?.text = tvShowEntity.creator
        contentBinding?.tvOverview?.text = tvShowEntity.overview

        contentBinding?.imagePoster?.let {
            Glide.with(this)
                .load(tvShowEntity.imagePath)
                .transform(RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(it)
        }

    }

    private fun setFavoriteState(state: Boolean) {
        if (state)
            fab_fav.setImageResource(R.drawable.ic_favorite_white)
        else
           fab_fav.setImageResource(R.drawable.ic_favorite_border_white)
    }
}