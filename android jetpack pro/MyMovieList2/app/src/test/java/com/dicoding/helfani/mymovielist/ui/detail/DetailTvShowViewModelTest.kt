package com.dicoding.helfani.mymovielist.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.utils.DataMovies
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
    private lateinit var tvShowObserver: Observer<TvShowEntity>


    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(movieRepository)
        viewModel.setSelectedTvShow(tvShowId)
    }

    @Test
    fun getTvShow() {
        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = dataTvShow

        `when`(movieRepository.getTvShow(tvShowId)).thenReturn(tvShow)
        val tvShowEntity = viewModel.getTvShow().value as TvShowEntity
        verify(movieRepository).getTvShow(tvShowId)

        assertNotNull(tvShowEntity)
        assertEquals(dataTvShow.tvShowId, tvShowEntity.tvShowId)
        assertEquals(dataTvShow.title, tvShowEntity.title)
        assertEquals(dataTvShow.genre, tvShowEntity.genre)
        assertEquals(dataTvShow.creator, tvShowEntity.creator)
        assertEquals(dataTvShow.overview, tvShowEntity.overview)
        assertEquals(dataTvShow.imagePath, tvShowEntity.imagePath)

        viewModel.getTvShow().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dataTvShow)
    }
}