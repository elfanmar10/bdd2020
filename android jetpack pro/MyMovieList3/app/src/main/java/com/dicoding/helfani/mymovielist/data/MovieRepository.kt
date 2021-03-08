package com.dicoding.helfani.mymovielist.data

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.helfani.mymovielist.data.source.local.LocalDataSource
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.data.source.remote.ApiResponse
import com.dicoding.helfani.mymovielist.data.source.remote.RemoteDataSource
import com.dicoding.helfani.mymovielist.data.source.remote.response.MovieResponse
import com.dicoding.helfani.mymovielist.data.source.remote.response.TvShowResponse
import com.dicoding.helfani.mymovielist.utils.AppExecutors
import com.dicoding.helfani.mymovielist.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : MovieDataSource {

    companion object {
        @Volatile
        private var instance : MovieRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors) : MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {

        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(movieResponses: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponses) {
                    val movie = MovieEntity(
                        response.movieId,
                        response.title,
                        response.date,
                        response.genre,
                        response.director,
                        response.overview,
                        response.imagePath
                    )

                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }

        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {

        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getAllTvShows()

            public override fun saveCallResult(tvShowResponses: List<TvShowResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in tvShowResponses) {
                    val tvShow = TvShowEntity(
                        response.tvShowId,
                        response.title,
                        response.genre,
                        response.creator,
                        response.overview,
                        response.imagePath
                    )

                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()

    }

    override fun getMovie(movieId: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovie(movieId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override fun saveCallResult(movieResponses: List<MovieResponse>) {
                lateinit var movie: MovieEntity
                for (response in movieResponses) {
                    if (response.movieId == movieId) {
                        movie = MovieEntity(
                            response.movieId,
                            response.title,
                            response.date,
                            response.genre,
                            response.director,
                            response.overview,
                            response.imagePath
                        )
                    }
                    localDataSource.getMovie(movieId)
                }
            }
        }.asLiveData()
    }

    override fun getTvShow(tvShowId: String): LiveData<Resource<TvShowEntity>>  {
        return object : NetworkBoundResource<TvShowEntity, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getTvShow(tvShowId)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getAllTvShows()

            override fun saveCallResult(tvShowResponses: List<TvShowResponse>) {
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
                    localDataSource.getTvShow(tvShowId)
                }
            }
        }.asLiveData()
    }

    override fun getFavoritedMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoritedMovies(), config).build()
    }

    override fun getFavoritedTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoritedTvShows(), config).build()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean)=
        appExecutors.diskIO().execute { localDataSource.setMovieFavorited(movie, state)}

    override fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setTvShowFavorited(tvShow, state) }
    }

}