package com.example.hbo_buddy_app

import android.app.Notification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.notification != null){
            val msg = remoteMessage.notification
            val title: String? = msg?.title
            val body: String? = msg?.body
            Notifications.displayNotification(applicationContext, title!!, body!!)
        }
    }

}