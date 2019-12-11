package com.example.hbo_buddy_app.api

import com.example.hbo_buddy_app.models.CoachProfile
import com.example.hbo_buddy_app.models.Message
import com.example.hbo_buddy_app.models.SendMessage
import com.example.hbo_buddy_app.models.Student
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/api/profile/coach")
    fun getAllCoachProfiles(): Call<ArrayList<CoachProfile>>
    @GET("/api/messages/{coachID}/{tutorantID}")
    fun getConversationBetweenBuddyAndTut(
        @Path("coachID")coachID: Int,
        @Path("tutorantID")tutorantID: Int
    ) : Call<ArrayList<Message>>
    @POST("/api/message")
    fun sendMessage(
        @Body sendMessage: SendMessage
    ) : Call<Any>
}