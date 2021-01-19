package com.dicoding.helfani.mymovielist.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
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

class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dataMovie = DataMovies.generateMovieList()[0]
    private val movieId = dataMovie.movieId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<MovieEntity>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
        viewModel.setSelectedMovie(movieId)
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dataMovie

        `when`(movieRepository.getMovie(movieId)).thenReturn(movie)
        val movieEntity = viewModel.getMovie().value as MovieEntity
        verify(movieRepository).getMovie(movieId)

        assertNotNull(movieEntity)
        assertEquals(dataMovie.movieId, movieEntity.movieId)
        assertEquals(dataMovie.title, movieEntity.title)
        assertEquals(dataMovie.date, movieEntity.date)
        assertEquals(dataMovie.genre, movieEntity.genre)
        assertEquals(dataMovie.director, movieEntity.director)
        assertEquals(dataMovie.overview, movieEntity.overview)
        assertEquals(dataMovie.imagePath, movieEntity.imagePath)

        viewModel.getMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dataMovie)
    }
}