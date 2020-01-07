package com.example.hbo_buddy_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.hbo_buddy_app.chat.ChatActivity
import com.example.hbo_buddy_app.models.CoachTutorantConnection
import com.example.hbo_buddy_app.models.Student
import com.example.hbo_buddy_app.retrofit.RetroFitService
import kotlinx.android.synthetic.main.activity_main_hbo.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainHBOActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hbo)

        val intent: Intent = getIntent()
        val student: Student = intent.getParcelableExtra("profile")

        test.text = student.firstName

        chatBtn.setOnClickListener{
            val intent = Intent(this, ChatActivity:: class.java)
            ContextCompat.startActivity(this, intent, null )
        }

        //check if someone matched

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

            }
        })
    }
}
