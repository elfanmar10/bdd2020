package com.dicoding.helfani.mymovielist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.data.source.remote.RemoteDataSource
import com.dicoding.helfani.mymovielist.data.source.remote.response.MovieResponse
import com.dicoding.helfani.mymovielist.data.source.remote.response.TvShowResponse

class FakeMovieRepository (private val remoteDataSource: RemoteDataSource) : MovieDataSource {

    override fun getAllMovies(): LiveData<List<MovieEntity>> {
        val movieResults = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getAllMovies(object: RemoteDataSource.LoadMovieCallback {
            override fun onAllMoviesReceived(movieResponses: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponses) {
                    val movie = MovieEntity(response.movieId,
                    response.title,
                    response.date,
                    response.genre,
                    response.director,
                    response.overview,
                    response.imagePath)

                movieList.add(movie)
                }
                movieResults.postValue(movieList)
            }
        })
        return movieResults
    }

    override fun getAllTvShows(): LiveData<List<TvShowEntity>> {
        val tvShowResults = MutableLiveData<List<TvShowEntity>>()
        remoteDataSource.getAllTvShows(object : RemoteDataSource.LoadTvShowCallback {
            override fun onAllTvShowsReceived(tvShowResponses: List<TvShowResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in tvShowResponses) {
                    val tvShow = TvShowEntity(response.tvShowId,
                        response.title,
                        response.genre,
                        response.creator,
                        response.overview,
                        response.imagePath)

                    tvShowList.add(tvShow)
                }
                tvShowResults.postValue(tvShowList)
            }
        })
        return tvShowResults
    }

    override fun getMovie(movieId: String) : LiveData<MovieEntity> {
        val movieResults = MutableLiveData<MovieEntity>()

        remoteDataSource.getAllMovies(object : RemoteDataSource.LoadMovieCallback {
            override fun onAllMoviesReceived(movieResponses: List<MovieResponse>) {
                lateinit var movie: MovieEntity
                for (response in movieResponses) {
                    if (response.movieId == movieId) {
                        movie = MovieEntity(response.movieId,
                                response.title,
                                response.date,
                                response.genre,
                                response.director,
                                response.overview,
                                response.imagePath)
                    }
                }
                movieResults.postValue(movie)
            }
        })
        return movieResults
    }

    override fun getTvShow(tvShowId: String): LiveData<TvShowEntity> {
        val tvResults = MutableLiveData<TvShowEntity>()

        remoteDataSource.getAllTvShows(object : RemoteDataSource.LoadTvShowCallback {
            override fun onAllTvShowsReceived(tvShowResponses: List<TvShowResponse>) {
                lateinit var tvShow: TvShowEntity
                for (response in tvShowResponses) {
                    if (response.tvShowId == tvShowId) {
                        tvShow = TvShowEntity(
                            response.tvShowId,
                            response.title,
                            response.genre,
                            response.creator,
                            response.overview,
                            response.imagePath
                        )
                    }
                }
                tvResults.postValue(tvShow)
            }

        })
        return tvResults
    }
}