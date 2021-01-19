package com.dicoding.helfani.mymovielist.di

import android.content.Context
import com.dicoding.helfani.mymovielist.data.MovieRepository
import com.dicoding.helfani.mymovielist.data.source.remote.RemoteDataSource
import com.dicoding.helfani.mymovielist.utils.JsonHelper

object Injection {

    fun provideRepository(context: Context) : MovieRepository {

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return MovieRepository.getInstance(remoteDataSource)
    }
}