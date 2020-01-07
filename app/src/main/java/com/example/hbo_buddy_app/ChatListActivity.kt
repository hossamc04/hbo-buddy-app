package com.example.hbo_buddy_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hbo_buddy_app.chat.ChatActivity
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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-tinderclonefa-test.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: RetroFitService = retrofit.create(RetroFitService::class.java)

        val call = api.getCoachTutorantConnections(student.studentID)
        call.enqueue(object: Callback<List<CoachTutorantConnection>> {
            override fun onFailure(call: Call<List<CoachTutorantConnection>>, t: Throwable) {
                //textView.text = t.message
            }

            override fun onResponse(call: Call<List<CoachTutorantConnection>>, response: Response<List<CoachTutorantConnection>>) {
                //testview.text = response.isSuccessful.toString()
                chatlist.layoutManager = LinearLayoutManager(this@ChatListActivity)
                val buddies = response.body()

                val clickInterface:ClickListener = object:ClickListener{
                    override fun onItemClick(position: Int) {
                        val intent: Intent = Intent(this@ChatListActivity, ChatActivity::class.java)
                        //intent.putExtra(INTENT_KEY_1, buddies!!.get(position))
                        startActivity(intent)
                    }
                }
                chatlist.adapter = ChatListAdapter(this@ChatListActivity, buddies!!, clickInterface)
                //chatlist.adapter.notifyDataSetChanged()

            }
        })
    }
}
