package com.dicoding.helfani.mymovielist.data

import androidx.lifecycle.LiveData
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity

interface MovieDataSource {

    fun getAllMovies(): LiveData<List<MovieEntity>>

    fun getAllTvShows(): LiveData<List<TvShowEntity>>

    fun getMovie(movieId: String): LiveData<MovieEntity>

    fun getTvShow(tvShowId: String) : LiveData<TvShowEntity>
}