package com.dicoding.helfani.mymovielist.utils

import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.source.local.entity.MovieEntity
import com.dicoding.helfani.mymovielist.data.source.local.entity.TvShowEntity
import com.dicoding.helfani.mymovielist.data.source.remote.response.MovieResponse
import com.dicoding.helfani.mymovielist.data.source.remote.response.TvShowResponse

object DataMovies {

    fun generateMovieList(): ArrayList<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(MovieEntity("m01",
                "A Star Is Born (2018)",
                "10/05/2018",
                "Romance Music",
                "Bradley Cooper",
                "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg"
        ))        

        return movies
    }

    fun generateTvShowList() : ArrayList<TvShowEntity> {
        val tvShow = ArrayList<TvShowEntity>()

        tvShow.add(TvShowEntity("tv01",
                "Arrow (2012)",
                "Crime Drama",
                "Greg Berlanti",
                "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg"
        ))      

        return tvShow
    }

    fun generateRemoteMovieList(): ArrayList<MovieResponse> {
        val movies = ArrayList<MovieResponse>()

        movies.add(MovieResponse("m01",
        "A Star Is Born (2018)",
        "10/05/2018",
        "Romance Music",
        "Bradley Cooper",
        "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wrFpXMNBRj2PBiN4Z5kix51XaIZ.jpg"
        ))       

        return movies
    }

    fun generateRemoteTvShowList() : ArrayList<TvShowResponse> {
        val tvShow = ArrayList<TvShowResponse>()

        tvShow.add(TvShowResponse("tv01",
        "Arrow (2012)",
        "Crime Drama",
        "Greg Berlanti",
        "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/gKG5QGz5Ngf8fgWpBsWtlg5L2SF.jpg"
        ))
      
        return tvShow
    }
}