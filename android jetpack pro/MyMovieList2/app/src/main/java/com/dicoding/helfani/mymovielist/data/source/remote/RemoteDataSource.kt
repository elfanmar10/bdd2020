package com.dicoding.helfani.mymovielist.data.source.remote

import android.os.Looper
import com.dicoding.helfani.mymovielist.utils.JsonHelper
import android.os.Handler
import com.dicoding.helfani.mymovielist.data.source.remote.response.MovieResponse
import com.dicoding.helfani.mymovielist.data.source.remote.response.TvShowResponse
import com.dicoding.helfani.mymovielist.utils.EspressoIdlingResource

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllMovies(callback: LoadMovieCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({
            callback.onAllMoviesReceived(jsonHelper.loadMovies())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getAllTvShows(callback: LoadTvShowCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({
            callback.onAllTvShowsReceived(jsonHelper.loadTvShow())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    interface LoadMovieCallback {
        fun onAllMoviesReceived(movieResponses: List<MovieResponse>)
    }

    interface LoadTvShowCallback {
        fun onAllTvShowsReceived(tvShowResponses: List<TvShowResponse>)
    }

}