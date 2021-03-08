package com.dicoding.helfani.mymovielist.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieentities")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "date")
	var date: String,

    @ColumnInfo(name = "genre")
    var genre: String,

    @ColumnInfo(name ="director")
	var director: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "imagePath")
    var imagePath: String,

    @ColumnInfo(name = "favorited")
    var favorited: Boolean = false
)
