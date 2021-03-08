package com.dicoding.helfani.mymovielist.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.vo.Resource

class TvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> = movieRepository.getAllTvShows()
}