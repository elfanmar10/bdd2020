package com.dicoding.helfani.mymovielist.ui.detail

import android.app.VoiceInteractor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.MovieEntity
import com.dicoding.helfani.mymovielist.databinding.ActivityDetailMovieBinding
import com.dicoding.helfani.mymovielist.databinding.ContentDetailMovieBinding
import com.dicoding.helfani.mymovielist.utils.DataMovies

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var detailContentBinding: ContentDetailMovieBinding

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailMovieBinding = ActivityDetailMovieBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailMovieBinding.detailContent

        setContentView(activityDetailMovieBinding.root)

        setSupportActionBar(activityDetailMovieBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailMovieViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getString(EXTRA_MOVIE)
            if(movieId != null) {
                viewModel.setSelectedMovie(movieId)
                populateMovie(viewModel.getMovie() as MovieEntity)
            }
        }
    }

    private fun populateMovie(movieEntity: MovieEntity) {
        detailContentBinding.tvTitle.text = movieEntity.title
        detailContentBinding.tvDate.text = movieEntity.date
        detailContentBinding.tvGenre.text = movieEntity.genre
        detailContentBinding.tvDirector.text = movieEntity.director
        detailContentBinding.tvOverview.text = movieEntity.overview

        Glide.with(this)
                .load(movieEntity.imagePath)
                .transform(RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                      .error((R.drawable.ic_error))
                .into(detailContentBinding.imagePoster)
    }
}