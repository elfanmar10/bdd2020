package com.dicoding.helfani.mymovielist.di

import android.content.Context
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.local.LocalDataSource
import com.dicoding.helfani.mymovielist.data.source.local.room.MovieDatabase
import com.dicoding.helfani.mymovielist.data.source.remote.RemoteDataSource
import com.dicoding.helfani.mymovielist.utils.AppExecutors
import com.dicoding.helfani.mymovielist.utils.JsonHelper

object Injection {

    fun provideRepository(context: Context) : MovieRepository {

        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}