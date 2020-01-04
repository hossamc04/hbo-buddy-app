package com.example.hbo_buddy_app

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.RemoteMessage

class Notifications (){
    companion object{
        fun displayNotification(context: Context, title: String, body: String){
            val builder = NotificationCompat.Builder(context, "Inholland Buddy App")
                .setContentTitle(title)
                .setContentText(body)
                //.setSmallIcon() img file
                .setChannelId("Inholland Buddy App")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            //channelid
            val not: NotificationManagerCompat = NotificationManagerCompat.from(context)
            not.notify(0, builder)
        }
    }
}