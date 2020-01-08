package com.example.hbo_buddy_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hbo_buddy_app.chat.data.ChatRepository
import com.example.hbo_buddy_app.models.CoachTutorantConnection
import com.example.hbo_buddy_app.models.Student
import okhttp3.OkHttpClient
import javax.inject.Inject

class ChatListViewModel @Inject constructor(
    private val repository : StudentRepository
): ViewModel() {
    fun getStudent(id: String, client: OkHttpClient): MutableLiveData<Student>{
        return repository.getStudent(id, client)
    }

}