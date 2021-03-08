package com.dicoding.helfani.mymovielist.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.di.Injection
import com.dicoding.helfani.mymovielist.ui.detail.DetailMovieViewModel
import com.dicoding.helfani.mymovielist.ui.detail.DetailTvShowViewModel
import com.dicoding.helfani.mymovielist.ui.favorite.movie.FavoriteMovieViewModel
import com.dicoding.helfani.mymovielist.ui.favorite.tvshow.FavoriteTvShowViewModel
import com.dicoding.helfani.mymovielist.ui.movie.MovieViewModel
import com.dicoding.helfani.mymovielist.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mMovieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) : ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                return TvShowViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                return DetailMovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) -> {
                return DetailTvShowViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                return FavoriteMovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                return FavoriteTvShowViewModel(mMovieRepository) as T
            }
            else -> throw Throwable("Unknown VieModel class: " + modelClass.name)
        }
    }
}