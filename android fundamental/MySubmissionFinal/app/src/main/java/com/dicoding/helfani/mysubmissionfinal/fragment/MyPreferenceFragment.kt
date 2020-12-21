package com.dicoding.helfani.mysubmissionfinal.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.helfani.mysubmissionfinal.R
import com.dicoding.helfani.mysubmissionfinal.receiver.AlarmReceiver

class MyPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var ALARM: String

    private lateinit var alarmPreference: SwitchPreference

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun init() {
        alarmReceiver = AlarmReceiver()
        ALARM = resources.getString(R.string.key_reminder)
        alarmPreference = findPreference<SwitchPreference> (ALARM) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == ALARM) {
            alarmPreference.isChecked = sharedPreferences.getBoolean(ALARM, false)
        }
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences

        alarmPreference.isChecked = sh.getBoolean(ALARM, false)
        if (alarmPreference.isChecked == true) {
            val repeatMessage = resources.getString(R.string.notif_message)
            alarmReceiver.setRepeatingAlarm(requireContext(), repeatMessage )

        } else {
            alarmReceiver.cancelAlarm(requireContext())
        }
    }
}