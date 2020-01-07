package com.example.hbo_buddy_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hbo_buddy_app.models.CoachTutorantConnection
import com.example.hbo_buddy_app.models.Student
import com.example.hbo_buddy_app.models.TutorantProfile
import com.example.hbo_buddy_app.retrofit.RetroFitService
import kotlinx.android.synthetic.main.activity_chat_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Array

class ChatListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val intent: Intent = getIntent()
        val student: Student = intent.getParcelableExtra("profile")
        val buddies = ArrayList<CoachTutorantConnection>()

        //chatlist.adapter = Adapter



        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-tinderclonefa-test.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: RetroFitService = retrofit.create(RetroFitService::class.java)

        val call = api.getCoachTutorantConnection(student.studentID)
        call.enqueue(object: Callback<CoachTutorantConnection> {
            override fun onFailure(call: Call<CoachTutorantConnection>, t: Throwable) {
                //textView.text = t.message
            }

            override fun onResponse(call: Call<CoachTutorantConnection>, response: Response<CoachTutorantConnection>) {
                testview.text = response.isSuccessful.toString()
            }
        })
    }
}
