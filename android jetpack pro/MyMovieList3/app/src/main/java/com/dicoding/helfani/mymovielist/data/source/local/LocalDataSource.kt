package com.dicoding.helfani.mymovielist.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val mMovieDao: MovieDao){

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getMovies()

    fun getFavoritedMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getFavoritedMovie()

    fun getMovie(movieId: String): LiveData<MovieEntity> =
        mMovieDao.getMovie(movieId)

    fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    fun setMovieFavorited(movie: MovieEntity, newState: Boolean) {
        movie.favorited = newState
        mMovieDao.updateMovie(movie)
    }

    fun getAllTvShows(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getTvShows()

    fun getFavoritedTvShows(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getFavoritedTvShow()

    fun getTvShow(tvShowId: String): LiveData<TvShowEntity> =
        mMovieDao.getTvShow(tvShowId)

    fun insertTvShows(tvshows: List<TvShowEntity>) = mMovieDao.insertTvShow(tvshows)

    fun setTvShowFavorited(tvshow: TvShowEntity, newState: Boolean) {
        tvshow.favorited = newState
        mMovieDao.updateTvShow(tvshow)
    }
}