package com.dicoding.helfani.mymovielist.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.vo.Resource

class DetailTvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

   val tvShowId = MutableLiveData<String>()

    fun setSelectedTvShow(tvShowId: String) {
        this.tvShowId.value = tvShowId
    }

    var tvShow: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(tvShowId) { mTvShowId ->
        movieRepository.getTvShow(mTvShowId)
    }

    fun setFavorite() {
        val tvShowResource = tvShow.value
        if(tvShowResource != null) {
            val tvShowEntity = tvShowResource.data

            if (tvShowEntity != null) {
                val newState = !tvShowEntity.favorited
                movieRepository.setTvShowFavorite(tvShowEntity, newState)
            }
        }

    }
}