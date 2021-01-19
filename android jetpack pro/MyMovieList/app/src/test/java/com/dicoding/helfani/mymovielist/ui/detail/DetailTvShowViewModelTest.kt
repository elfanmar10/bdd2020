package com.dicoding.helfani.mymovielist.ui.detail

import com.dicoding.helfani.mymovielist.utils.DataMovies
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel
    private val dataTvShow = DataMovies.generateTvShowList()[0]
    private val tvShowId = dataTvShow.tvShowId

    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel()
        viewModel.setSelectedTvShow(tvShowId)
    }

    @Test
    fun getTvShow() {
        viewModel.setSelectedTvShow(dataTvShow.tvShowId)
        val tvShowEntity = viewModel.getTvShow()
        assertNotNull(tvShowEntity)
        assertEquals(dataTvShow.tvShowId, tvShowEntity.tvShowId)
        assertEquals(dataTvShow.title, tvShowEntity.title)
        assertEquals(dataTvShow.genre, tvShowEntity.genre)
        assertEquals(dataTvShow.creator, tvShowEntity.creator)
        assertEquals(dataTvShow.overview, tvShowEntity.overview)
        assertEquals(dataTvShow.imagePath, tvShowEntity.imagePath)
    }
}