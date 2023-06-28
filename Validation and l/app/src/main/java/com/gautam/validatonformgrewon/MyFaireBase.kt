package com.gautam.validatonformgrewon

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFaireBase : FirebaseMessagingService() {

    private val chanal = "Notification"
    private val nameofchanal = "notification"
    private val chanal_description = " description"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification!!.title
        val messagebody = message.notification!!.body
        Log.e("TAG", "onMessageReceived: " + message.data.toString())

        showNotification(title, messagebody)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(title: String?, message: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                chanal,
                nameofchanal,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = chanal_description
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            // register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("messagebody", message)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder =
            NotificationCompat.Builder(this, chanal).setContentTitle(title).setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24).setStyle(
                    NotificationCompat.BigTextStyle().bigText(message)
                ).setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))

        var notificationCompat = NotificationManagerCompat.from(this)
        notificationCompat.notify(1, builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TOKEN", "onNewToken: $token")
    }
}
