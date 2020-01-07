package com.example.hbo_buddy_app.chat

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hbo_buddy_app.chat.data.ChatRepository
import com.example.hbo_buddy_app.models.CoachProfile
import com.example.hbo_buddy_app.models.Message
import com.example.hbo_buddy_app.models.MessageSend
import com.example.hbo_buddy_app.select_buddy.data.BuddyProfileRepository
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChatViewModel @Inject constructor(
    private val repository : ChatRepository
): ViewModel()  {

    fun getAllMesages(coachID : String, studentId: String): MutableLiveData<ArrayList<Message>> {
        return repository.getMessagesLiveData(coachID, studentId)
    }

    fun onSendMessage(studentId: String, coachID: String, message: String){

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val sm = MessageSend(currentDate, currentDate, message, coachID, studentId, "text")

        repository.sendMessage(sm)

        //repository.sendMessage(SendMessage("10/12/2019","10/12/2019", message, 590873,123456,  "text"))
    }

}