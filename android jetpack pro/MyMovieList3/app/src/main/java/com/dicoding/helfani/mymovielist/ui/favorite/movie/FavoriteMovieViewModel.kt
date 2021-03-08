package com.dicoding.helfani.mymovielist.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavorites(): LiveData<PagedList<MovieEntity>> {
        return movieRepository.getFavoritedMovies()
    }

    fun setFavorite(movieEntity: MovieEntity) {
        val newState = !movieEntity.favorited
        movieRepository.setMovieFavorite(movieEntity, newState)
    }
 }