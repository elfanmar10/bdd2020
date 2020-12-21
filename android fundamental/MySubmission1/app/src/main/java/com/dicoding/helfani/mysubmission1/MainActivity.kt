package com.dicoding.helfani.mysubmission1

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import layout.User

class MainActivity : AppCompatActivity() {

    private var title: String = "Github User's"
    private lateinit var adapter: UserAdapter

    private lateinit var dataAvatar: TypedArray
    private lateinit var dataUsername: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataFollower: Array<String>

    private var usernames = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.setTitle(title);

        adapter = UserAdapter(this)
        lv_list.adapter = adapter

        prepare()
        addItem()

        }

    private fun prepare() {
        dataAvatar = resources.obtainTypedArray(R.array.avatar)
        dataUsername = resources.getStringArray(R.array.username)
        dataName = resources.getStringArray(R.array.name)
        dataFollower = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataCompany = resources.getStringArray(R.array.company)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepository = resources.getStringArray(R.array.repository)

    }

    private fun addItem() {
        for (position in dataUsername.indices) {
            val user = User(
                dataAvatar.getResourceId(position, -1),
                dataUsername[position],
                dataName[position],
                dataFollower[position],
                dataFollowing[position],
                dataCompany[position],
                dataLocation[position],
                dataRepository[position]
            )
            usernames.add(user)

            lv_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val detailIntent = Intent(this@MainActivity, UserDetail::class.java)
                detailIntent.putExtra(UserDetail.EXTRA_USER, usernames[position])
                startActivity(detailIntent)
        }
        adapter.usernames = usernames
     }

     dataAvatar.recycle()

   }

}