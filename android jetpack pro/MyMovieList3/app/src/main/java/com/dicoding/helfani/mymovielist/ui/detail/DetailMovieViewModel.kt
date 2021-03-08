package com.dicoding.helfani.mymovielist.ui.detail

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.vo.Resource

class DetailMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val movieId = MutableLiveData<String>()


    fun setSelectedMovie(movieId: String) {
        this.movieId.value = movieId
    }

    var movie: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) { mMovieId ->
        movieRepository.getMovie(mMovieId)
    }

    fun setFavorite() {
        val movieResource = movie.value
        if (movieResource != null) {
            val movieEntity = movieResource.data

            if (movieEntity != null) {
            val newState = !movieEntity.favorited
            movieRepository.setMovieFavorite(movieEntity, newState)
            }
        }
    }
}