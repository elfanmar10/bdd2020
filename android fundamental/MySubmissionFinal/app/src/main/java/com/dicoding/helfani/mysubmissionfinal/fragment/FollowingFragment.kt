package com.dicoding.helfani.mysubmissionfinal.fragment

import com.dicoding.helfani.mysubmissionfinal.entity.UserItems
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.helfani.mysubmissionfinal.R
import com.dicoding.helfani.mysubmissionfinal.adapter.UserAdapter
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray


class FollowingFragment : Fragment() {

    private lateinit var adapter: UserAdapter

    private val listFollowing = ArrayList<UserItems>()

    companion object {
        private const val ARG_USERNAME = "username"
        val TAG = FollowingFragment::class.java.simpleName
        const val  GITHUB_TOKEN = "BuildConfig.GITHUB_TOKEN"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
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
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.adapter = adapter

        val username = arguments?.getString(ARG_USERNAME)
        getFollowing(username)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                Toast.makeText(activity,"Anda memilih " + data.username, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getFollowing(username: String?) {

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", GITHUB_TOKEN)
        client.addHeader("User-Agent", "request")
        progressBarFollowing.visibility = View.VISIBLE

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                //Jika koneksi berhasil
                progressBarFollowing.visibility = View.INVISIBLE

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
                        listFollowing.add(userItems)
                    }
                    //set data ke adapter
                    adapter.setData(listFollowing)

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
               progressBarFollowing.visibility = View.INVISIBLE
                Log.d("OnFailure", error.message.toString())
            }
        })
    }
}

