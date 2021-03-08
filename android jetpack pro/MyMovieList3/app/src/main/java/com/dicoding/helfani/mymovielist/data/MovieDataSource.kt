package com.dicoding.helfani.mymovielist.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.vo.Resource

interface MovieDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getFavoritedMovies(): LiveData<PagedList<MovieEntity>>

    fun setMovieFavorite(movie: MovieEntity, state: Boolean)

    fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getFavoritedTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean)

    fun getMovie(movieId: String): LiveData<Resource<MovieEntity>>

    fun getTvShow(tvShowId: String) : LiveData<Resource<TvShowEntity>>

}