package com.dicoding.helfani.mymovielist.data.source.local.entity

import android.graphics.drawable.Drawable

data class MovieEntity(
    var movieId: String,
    var title: String,
	var date: String,
    var genre: String,
	var director: String,
    var overview: String,
    var imagePath: String
)
