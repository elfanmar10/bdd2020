package com.dicoding.helfani.mymovielist.ui.detail

import androidx.lifecycle.ViewModel
import com.dicoding.helfani.mymovielist.data.MovieEntity
import com.dicoding.helfani.mymovielist.utils.DataMovies

class DetailMovieViewModel : ViewModel() {

    private lateinit var movieId: String

    fun setSelectedMovie(movieId: String) {
        this.movieId = movieId
    }

    fun getMovie(): MovieEntity {
        lateinit var movie: MovieEntity
        val moviesEntities = DataMovies.generateMovieList()
        for (movieEntity in moviesEntities) {
            if (movieEntity.movieId == movieId) {
                movie = movieEntity
            }
        }
        return movie
    }
}