package com.dicoding.helfani.mysubmissionfinal.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dicoding.helfani.mysubmissionfinal.R
import com.dicoding.helfani.mysubmissionfinal.activity.MainActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE = "message"

        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val title = TYPE_REPEATING

        //Untuk menampilkan notification
        showAlarmNotification(context, title, message, NOTIFICATION_ID)
    }

    // Gunakan metode ini untuk menampilkan notifikasi
    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String?,
        notifId: Int
    ) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "Daily Reminder"

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications_white_48dp)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /*Create or update. */
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        val notificationBuilder = builder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder)
    }

    // Metode ini digunakan untuk menjalankan alarm repeating
    fun setRepeatingAlarm(context: Context, message: String) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }

        //val pendingIntent = PendingIntent.getService(context, NOTIFICATION_ID, intent, 0)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

    }

}