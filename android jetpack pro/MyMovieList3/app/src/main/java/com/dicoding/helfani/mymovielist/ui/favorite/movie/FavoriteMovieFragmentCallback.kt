package com.dicoding.helfani.mymovielist.ui.favorite.movie

import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity

interface FavoriteMovieFragmentCallback {
    fun onShareClick(movie: MovieEntity)

}
