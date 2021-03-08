package com.dicoding.helfani.mymovielist.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.utils.DataMovies
import com.dicoding.helfani.mymovielist.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel
    private val dataTvShow = DataMovies.generateTvShowList()[0]
    private val tvShowId = dataTvShow.tvShowId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TvShowEntity>>


    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(movieRepository)
        viewModel.setSelectedTvShow(tvShowId)
    }

    @Test
    fun getTvShow() {
        val dataTvShow = Resource.success(DataMovies.generateTvShow(dataTvShow, true))
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dataTvShow

        `when`(movieRepository.getTvShow(tvShowId)).thenReturn(tvShow)

        viewModel.tvShow.observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dataTvShow)
    }
}