package com.dicoding.helfani.mymovielist.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    var movieId: String,
    var title: String,
    var date: String,
    var genre: String,
    var director: String,
    var overview: String,
    var imagePath: String
): Parcelable
