package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class Message(
    val messageID: Int? = null,
    val created: String,
    val lastModified: String,
    val payload: String,
    val receiverID: Int,
    val senderID: Int,
    val type: String
)

data class SendMessage(
    val created: String,
    val lastModified: String,
    val payload: String,
    val receiverID: Int,
    val senderID: Int,
    val type: String
)