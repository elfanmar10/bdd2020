package com.dicoding.helfani.mymovielist.utils

import com.dicoding.helfani.mymovielist.R
import com.dicoding.helfani.mymovielist.data.MovieEntity
import com.dicoding.helfani.mymovielist.data.TvShowEntity

object DataMovies {

    fun generateMovieList(): ArrayList<MovieEntity> {
        val movies = ArrayList<MovieEntity>()

        movies.add(MovieEntity("m01",
        "A Star Is Born (2018)",
        "10/05/2018",
        "Romance Music ",
        "Bradley Cooper",
        "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
        R.drawable.movie_a_star_is_born
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
        R.drawable.tvshow_arrow
        ))       

        return tvShow
    }
}