package com.dicoding.helfani.mymovielist.ui.detail

import androidx.lifecycle.ViewModel
import com.dicoding.helfani.mymovielist.data.TvShowEntity
import com.dicoding.helfani.mymovielist.utils.DataMovies

class DetailTvShowViewModel : ViewModel() {

    private lateinit var tvShowId: String

    fun setSelectedTvShow(tvShowId: String) {
        this.tvShowId = tvShowId
    }

    fun getTvShow(): TvShowEntity {
        lateinit var tvShow: TvShowEntity
        val tvShowEntities = DataMovies.generateTvShowList()
        for (tvShowEntity in tvShowEntities) {
            if (tvShowEntity.tvShowId == tvShowId) {
                tvShow = tvShowEntity
            }
        }
        return tvShow
    }
}