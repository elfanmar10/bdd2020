package com.dicoding.helfani.mysubmissionfinal.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.helfani.mysubmissionfinal.R
import com.dicoding.helfani.mysubmissionfinal.fragment.MyPreferenceFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.settings)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, MyPreferenceFragment()).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}