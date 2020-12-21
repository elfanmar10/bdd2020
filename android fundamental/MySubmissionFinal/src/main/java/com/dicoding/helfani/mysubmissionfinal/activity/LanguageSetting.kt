package com.dicoding.helfani.mysubmissionfinal.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.helfani.mysubmissionfinal.R

class LanguageSetting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_setting)
        val localeIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(localeIntent)
        finish()
    }
}