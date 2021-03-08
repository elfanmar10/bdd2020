package com.dicoding.helfani.mymovielist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.helfani.mymovielist.data.source.local.LocalDataSource
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.data.source.remote.RemoteDataSource
import com.dicoding.helfani.mymovielist.utils.AppExecutors
import com.dicoding.helfani.mymovielist.utils.DataMovies
import com.dicoding.helfani.mymovielist.utils.LiveDataTestUtil
import com.dicoding.helfani.mymovielist.utils.PagedListUtil
import com.dicoding.helfani.mymovielist.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponses = DataMovies.generateRemoteMovieList()
    private val movieId = movieResponses[0].movieId

    private val tvShowResponses = DataMovies.generateRemoteTvShowList()
    private val tvShowId = tvShowResponses[0].tvShowId

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataMovies.generateMovieList()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getAllTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataMovies.generateTvShowList()))
        verify(local).getAllTvShows()
        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getMovie() {
       val movieEntity = MutableLiveData<MovieEntity>()
       movieEntity.value = DataMovies.generateMovie(DataMovies.generateMovieList()[0], false)
       `when`(local.getMovie(movieId)).thenReturn(movieEntity)

        val movieEntities = LiveDataTestUtil.getValue(movieRepository.getMovie(movieId))

        verify(local).getMovie(movieId)

        assertNotNull(movieEntities.data)
        assertNotNull(movieEntities.data?.title)
        assertEquals(movieResponses[0].title, movieEntities.data?.title)
    }

    @Test
    fun getTvShow() {
        val tvShowEntity = MutableLiveData<TvShowEntity>()
        tvShowEntity.value = DataMovies.generateTvShow(DataMovies.generateTvShowList()[0], false)
        `when`(local.getTvShow(tvShowId)).thenReturn(tvShowEntity)

        val tvShowEntities = LiveDataTestUtil.getValue(movieRepository.getTvShow(tvShowId))

        verify(local).getTvShow(tvShowId)

        assertNotNull(tvShowEntities.data)
        assertNotNull(tvShowEntities.data?.title)
        assertEquals(tvShowResponses[0].title, tvShowEntities.data?.title)

    }

    @Test
    fun getFavoritedMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoritedMovies()).thenReturn(dataSourceFactory)
        movieRepository.getFavoritedMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataMovies.generateMovieList()))
        verify(local).getFavoritedMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoritedTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoritedTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getFavoritedTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataMovies.generateTvShowList()))
        verify(local).getFavoritedTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }
}