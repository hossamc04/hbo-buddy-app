package com.example.hbo_buddy_app.models


import com.google.gson.annotations.SerializedName

data class MessageSend(
    val created: String,
    val lastModified: String,
    val payload: String,
    val receiverID: String,
    val senderID: String,
    val type: String
)