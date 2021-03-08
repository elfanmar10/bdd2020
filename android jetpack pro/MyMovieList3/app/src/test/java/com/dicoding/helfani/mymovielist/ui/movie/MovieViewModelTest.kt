package com.dicoding.helfani.mymovielist.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
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

class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getMovies() {
        val dataMovies = Resource.success(pagedList)
        `when`(dataMovies.data?.size).thenReturn(15)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dataMovies

        `when`(movieRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value?.data
        verify(movieRepository).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(15, movieEntities?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dataMovies)
    }
}