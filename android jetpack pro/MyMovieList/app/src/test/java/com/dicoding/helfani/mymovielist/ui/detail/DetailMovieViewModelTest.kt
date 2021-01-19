package com.dicoding.helfani.mymovielist.ui.detail

import com.dicoding.helfani.mymovielist.utils.DataMovies
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dataMovie = DataMovies.generateMovieList()[0]
    private val movieId = dataMovie.movieId

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel()
        viewModel.setSelectedMovie(movieId)
    }

    @Test
    fun getMovie() {
        viewModel.setSelectedMovie(dataMovie.movieId)
        val movieEntity = viewModel.getMovie()
        assertNotNull(movieEntity)
        assertEquals(dataMovie.movieId, movieEntity.movieId)
        assertEquals(dataMovie.title, movieEntity.title)
        assertEquals(dataMovie.date, movieEntity.date)
        assertEquals(dataMovie.genre, movieEntity.genre)
        assertEquals(dataMovie.director, movieEntity.director)
        assertEquals(dataMovie.overview, movieEntity.overview)
        assertEquals(dataMovie.imagePath, movieEntity.imagePath)
    }
}