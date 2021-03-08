package com.dicoding.helfani.mymovielist.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity

class FavoriteTvShowViewModel(private val movieRepository: MovieRepository): ViewModel() {

    fun getFavorites(): LiveData<PagedList<TvShowEntity>> {
        return movieRepository.getFavoritedTvShows()
    }

    fun setFavorite(tvShowEntity: TvShowEntity) {
        val newState = !tvShowEntity.favorited
        movieRepository.setTvShowFavorite(tvShowEntity, newState)
    }
}
