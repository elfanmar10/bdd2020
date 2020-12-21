package  com.dicoding.helfani.consumerapp.helper

import com.dicoding.helfani.consumerapp.entity.UserItems
import android.database.Cursor
import  com.dicoding.helfani.consumerapp.db.DatabaseContract.UserColumns

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<UserItems> {
        val userList = ArrayList<UserItems>()

        userCursor?.apply {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(UserColumns._ID))
                val avatar = getString(getColumnIndexOrThrow(UserColumns.AVATAR))
                val username = getString(getColumnIndexOrThrow(UserColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(UserColumns.NAME))
                val company = getString(getColumnIndexOrThrow(UserColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(UserColumns.LOCATION))
                val followers = getString(getColumnIndexOrThrow(UserColumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(UserColumns.FOLLOWING))
                val repository = getString(getColumnIndexOrThrow(UserColumns.REPOSITORY))

                userList.add(UserItems(id, avatar, username, name, company, location, followers, following, repository))
            }
        }
        return userList
    }

    fun mapCursorToObject(userCursor: Cursor?) : UserItems {
        var user = UserItems()
            userCursor?.apply {
                moveToFirst()
                val id = getString(getColumnIndexOrThrow(UserColumns._ID))
                val avatar = getString(getColumnIndexOrThrow(UserColumns.AVATAR))
                val username = getString(getColumnIndexOrThrow(UserColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(UserColumns.NAME))
                val company = getString(getColumnIndexOrThrow(UserColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(UserColumns.LOCATION))
                val followers = getString(getColumnIndexOrThrow(UserColumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(UserColumns.FOLLOWING))
                val repository = getString(getColumnIndexOrThrow(UserColumns.REPOSITORY))

                user = UserItems(id, avatar, username, name, company, location, followers, following, repository)
            }
        return user
    }
}