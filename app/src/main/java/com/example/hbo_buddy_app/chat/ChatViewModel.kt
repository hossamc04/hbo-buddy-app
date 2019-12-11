package com.example.hbo_buddy_app.chat

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hbo_buddy_app.chat.data.ChatRepository
import com.example.hbo_buddy_app.models.CoachProfile
import com.example.hbo_buddy_app.models.Message
import com.example.hbo_buddy_app.models.SendMessage
import com.example.hbo_buddy_app.select_buddy.data.BuddyProfileRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val repository : ChatRepository
): ViewModel()  {

    fun getAllMesages(): MutableLiveData<ArrayList<Message>> {
        return repository.getMessagesLiveData()
    }

    fun onSendMessage(message: String){
        repository.sendMessage(SendMessage("10/12/2019","10/12/2019", message, 590873,123456,  "text"))
    }

}