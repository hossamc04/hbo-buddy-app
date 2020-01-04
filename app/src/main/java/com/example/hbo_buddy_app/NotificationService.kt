package com.example.hbo_buddy_app

import android.app.Notification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        if (p0.notification != null){
            val msg = p0.notification
            val title: String? = msg?.title
            val body: String? = msg?.body
            Notifications.displayNotification(applicationContext, title!!, body!!)
        }
    }

}