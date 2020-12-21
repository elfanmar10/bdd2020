package com.dicoding.helfani.mysubmissionfinal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.helfani.mysubmissionfinal.db.DatabaseContract.UserColumns
import com.dicoding.helfani.mysubmissionfinal.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbgithubapp"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                        "(${UserColumns._ID} TEXT PRIMARY KEY," +
                        "${UserColumns.AVATAR} TEXT NOT NULL," +
                        "${UserColumns.USERNAME} TEXT NOT NULL," +
                        "${UserColumns.NAME} TEXT NOT NULL," +
                        "${UserColumns.COMPANY} TEXT NOT NULL," +
                        "${UserColumns.LOCATION} TEXT NOT NULL," +
                        "${UserColumns.FOLLOWERS} TEXT NOT NULL," +
                        "${UserColumns.FOLLOWING} TEXT NOT NULL," +
                        "${UserColumns.REPOSITORY} TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}