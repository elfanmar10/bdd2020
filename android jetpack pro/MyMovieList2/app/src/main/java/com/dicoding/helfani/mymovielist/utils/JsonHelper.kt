package com.dicoding.helfani.mymovielist.utils

import android.content.Context
import com.dicoding.helfani.mymovielist.data.source.remote.response.MovieResponse
import com.dicoding.helfani.mymovielist.data.source.remote.response.TvShowResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(filename: String): String? {
        return try {
            val `is` = context.assets.open(filename)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): List<MovieResponse> {
        val list = ArrayList<MovieResponse>()
        try {
            val responseObject =JSONObject(parsingFileToString("MovieResponses.json").toString())
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                val movieId = movie.getString("movieId")
                val title = movie.getString("title")
                val date = movie.getString("date")
                val genre = movie.getString("genre")
                val director = movie.getString("director")
                val overview = movie.getString("overview")
                val imagePath = movie.getString("imagePath")

                val movieResponse = MovieResponse(movieId, title, date, genre, director, overview, imagePath)
                list.add(movieResponse)

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun loadTvShow(): List<TvShowResponse> {
        val list = ArrayList<TvShowResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("TvShowResponses.json").toString())
            val listArray = responseObject.getJSONArray("tvshow")
            for (i in 0 until listArray.length()) {
                val tvShow = listArray.getJSONObject(i)

                val tvShowId = tvShow.getString("tvShowId")
                val title = tvShow.getString("title")
                val genre = tvShow.getString("genre")
                val creator = tvShow.getString("creator")
                val overview = tvShow.getString("overview")
                val imagePath = tvShow.getString("imagePath")

                val tvShowResponse = TvShowResponse(tvShowId, title, genre, creator, overview, imagePath)
                list.add(tvShowResponse)

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }
}

