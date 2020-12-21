package  com.dicoding.helfani.consumerapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.dicoding.helfani.mysubmissionfinal"
    const val SCHEME = "content"

     class UserColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite"
            const val _ID = "_id"
            const val AVATAR = "avatar"
            const val USERNAME = "username"
            const val NAME = "name"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val REPOSITORY = "repository"

            // Untuk membuat URI content://com.dicoding.helfani.mysubmissionfinal/favorite
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}