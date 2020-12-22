package com.dicoding.helfani.mysubmission2

import UserItems
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.helfani.mysubmission2.MainActivity.Companion.GITHUB_TOKEN
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_follower.*
import org.json.JSONArray
import java.lang.Exception

class FollowerFragment : Fragment() {

    private lateinit var adapter: UserAdapter

    private val listFollower = ArrayList<UserItems>()

   companion object {
        private val ARG_USERNAME ="username"
        val TAG = MainActivity::class.java.simpleName

        fun newInstance(username: String?): FollowerFragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        rvFollower.layoutManager = LinearLayoutManager(activity)
        rvFollower.adapter = adapter

        val username = arguments?.getString(ARG_USERNAME)
        getFollowers(username)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                Toast.makeText(activity,"Anda memilih " + data.username, Toast.LENGTH_SHORT).show()

            }
        })

    }

    private fun getFollowers(username: String?) {

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", GITHUB_TOKEN)
        client.addHeader("User-Agent", "request")
        progressBarFollower.visibility = View.VISIBLE

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                //Jika koneksi berhasil
                progressBarFollower.visibility = View.INVISIBLE

                try {
                    //parsing json
                    val result = String(responseBody)
                    Log.d(TAG,result)

                    val items = JSONArray(result)

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.username = item.getString("login")
                        userItems.avatar = item.getString("avatar_url")
                        listFollower.add(userItems)

                    }
                    //set data ke adapter
                    adapter.setData(listFollower)

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
                progressBarFollower.visibility = View.INVISIBLE
                Log.d("OnFailure", error.message.toString())
            }
        })
    }

}