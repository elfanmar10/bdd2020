package com.dicoding.helfani.mymovielist.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dataMovie = DataMovies.generateMovieList()[0]
    private val movieId = dataMovie.movieId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
        viewModel.setSelectedMovie(movieId)
    }

    @Test
    fun getMovie() {
        val dataMovie = Resource.success(DataMovies.generateMovie(dataMovie, true))
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dataMovie

        `when`(movieRepository.getMovie(movieId)).thenReturn(movie)
        viewModel.movie.observeForever(movieObserver)
        verify(movieObserver).onChanged(dataMovie)

    }
}