package com.dicoding.helfani.mymovielist.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.ui.favorite.movie.FavoriteMovieViewModel
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest {

    private lateinit var viewModel: FavoriteMovieViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(movieRepository)
    }

    @Test
    fun getFavoritedMovie() {
        val dataMovies = pagedList
        `when`(dataMovies.size).thenReturn(15)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dataMovies

        `when`(movieRepository.getFavoritedMovies()).thenReturn(movies)
        val movieEntities = viewModel.getFavorites().value
        verify(movieRepository).getFavoritedMovies()
        assertNotNull(movieEntities)
        assertEquals(15, movieEntities?.size)

        viewModel.getFavorites().observeForever(observer)
        verify(observer).onChanged(dataMovies)

    }

}