package com.dicoding.helfani.mymovielist.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.databinding.ActivityDetailMovieBinding
import com.dicoding.helfani.mymovielist.viewmodel.ViewModelFactory
import com.dicoding.helfani.mymovielist.vo.Status
import kotlinx.android.synthetic.main.content_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private var _activityDetailMovieBinding: ActivityDetailMovieBinding? = null

    private val mainBinding get() = _activityDetailMovieBinding
    private val contentBinding get() = _activityDetailMovieBinding?.detailContent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailMovieBinding = ActivityDetailMovieBinding.inflate(layoutInflater)

        setContentView(mainBinding?.root)

        setSupportActionBar(mainBinding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getString(EXTRA_MOVIE)
            if (movieId != null) {
                viewModel.setSelectedMovie(movieId)

                viewModel.movie.observe(this, { movieResource ->
                    if (movieResource != null) {
                        when (movieResource.status) {
                            Status.LOADING -> mainBinding?.progressBar?.visibility = View.VISIBLE
                            Status.SUCCESS -> if (movieResource.data != null) {
                                mainBinding?.progressBar?.visibility = View.GONE
                                mainBinding?.content?.visibility = View.VISIBLE
                                val state = movieResource.data.favorited
                                setFavoriteState(state)

                                populateMovie(movieResource.data)
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


    private fun populateMovie(movieEntity: MovieEntity) {
        contentBinding?.tvTitle?.text = movieEntity.title
        contentBinding?.tvDate?.text = movieEntity.date
        contentBinding?.tvGenre?.text = movieEntity.genre
        contentBinding?.tvDirector?.text = movieEntity.director
        contentBinding?.tvOverview?.text = movieEntity.overview

        contentBinding?.imagePoster?.let {
            Glide.with(this)
                .load(movieEntity.imagePath)
                .transform(RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error((R.drawable.ic_error))
                .into(it)

        }



    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            fab_fav.setImageResource(R.drawable.ic_favorite_white)
        }
        else {
            fab_fav.setImageResource(R.drawable.ic_favorite_border_white)
        }
    }
}