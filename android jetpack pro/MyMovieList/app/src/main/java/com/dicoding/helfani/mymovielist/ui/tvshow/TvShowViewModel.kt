package com.dicoding.helfani.mymovielist.ui.tvshow

import androidx.lifecycle.ViewModel
import com.dicoding.helfani.mymovielist.data.TvShowEntity
import com.dicoding.helfani.mymovielist.utils.DataMovies

class TvShowViewModel : ViewModel() {

    fun getTvShows(): List<TvShowEntity> = DataMovies.generateTvShowList()
}