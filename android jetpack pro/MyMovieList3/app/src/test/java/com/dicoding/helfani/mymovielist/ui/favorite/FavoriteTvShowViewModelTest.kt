package com.dicoding.helfani.mymovielist.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.ui.favorite.tvshow.FavoriteTvShowViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowViewModelTest {

    private lateinit var viewModel: FavoriteTvShowViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(movieRepository)
    }

    @Test
    fun getFavoritedTvShow() {
        val dataMovies = pagedList
        `when`(dataMovies.size).thenReturn(15)
        val tvShows = MutableLiveData<PagedList<TvShowEntity>>()
        tvShows.value = dataMovies

        `when`(movieRepository.getFavoritedTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getFavorites().value
        verify(movieRepository).getFavoritedTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(15, tvShowEntities?.size)

        viewModel.getFavorites().observeForever(observer)
        verify(observer).onChanged(dataMovies)
    }
}