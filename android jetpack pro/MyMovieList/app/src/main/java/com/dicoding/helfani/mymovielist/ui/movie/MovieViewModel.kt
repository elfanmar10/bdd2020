package com.dicoding.helfani.mymovielist.ui.movie

import androidx.lifecycle.ViewModel
import com.dicoding.helfani.mymovielist.data.MovieEntity
import com.dicoding.helfani.mymovielist.utils.DataMovies

class MovieViewModel : ViewModel() {

    fun getMovies(): List<MovieEntity> = DataMovies.generateMovieList()
}