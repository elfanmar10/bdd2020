package com.dicoding.helfani.mymovielist.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowResponse(
    var tvShowId: String,
    var title: String,
    var genre: String,
    var creator: String,
    var overview: String,
    var imagePath: String
) : Parcelable
