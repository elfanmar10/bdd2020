package com.dicoding.helfani.mymovielist.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentities")
data class TvShowEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "tvShowId")
        var tvShowId: String,

        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "genre")
        var genre: String,

        @ColumnInfo(name = "creator")
        var creator: String,

        @ColumnInfo(name = "overview")
        var overview: String,

        @ColumnInfo(name = "imagePath")
        var imagePath: String,

        @ColumnInfo(name = "favorited")
        var favorited: Boolean = false
)
