package com.dicoding.helfani.mysubmissionfinal.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.helfani.mysubmissionfinal.activity.MainActivity
import com.dicoding.helfani.mysubmissionfinal.entity.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel: ViewModel() {

    companion object {
        const val  GITHUB_TOKEN = "BuildConfig.GITHUB_TOKEN"
    }

    val listUsers = MutableLiveData<ArrayList<UserItems>>()

    fun setUser(username: String) {
        val listUser = ArrayList<UserItems>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", GITHUB_TOKEN)
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                //Jika koneksi berhasil
                try {
                    //parsing json
                    val result = String(responseBody)
                    Log.d(MainActivity.TAG,result)
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.avatar = item.getString("avatar_url")
                        userItems.username = item.getString("login")

                        listUser.add(userItems)
                    }

                    listUsers.postValue(listUser)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                //Jika koneksi gagal
                Log.d("OnFailure", error.message.toString())
            }
        })
    }

    fun getUser(): LiveData<ArrayList<UserItems>> {
        return listUsers
    }
}

