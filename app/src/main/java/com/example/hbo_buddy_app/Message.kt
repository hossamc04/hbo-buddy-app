package com.example.hbo_buddy_app

import java.util.*

class Message (val messageID: Long, val type: String, val payload: String, val created: Date,
               val lastModified: Date, val senderID: Int, val receiverID: Int) {
}