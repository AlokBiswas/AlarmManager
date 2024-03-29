package com.alokbiswas.alarmmanager

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat

class MyAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val mBuilder = NotificationCompat.Builder(context!!)
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle("Alarm Manager")
            .setContentText(intent?.getStringExtra(EXTRA_PESAN))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val v = longArrayOf(500, 1000)
        mBuilder.setVibrate(v)

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        mBuilder.setSound(uri)

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = 30103
        mNotificationManager.notify(id, mBuilder.build())


    }

}