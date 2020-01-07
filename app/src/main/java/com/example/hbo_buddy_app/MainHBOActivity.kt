package com.example.hbo_buddy_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hbo_buddy_app.models.Student
import com.example.hbo_buddy_app.retrofit.RetroFitService
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

        if (student.studentID.toInt() != 3){

        }


        //check if user is hbo student.
        //check if someone matched

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-tinderclonefa-test.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: RetroFitService = retrofit.create(RetroFitService::class.java)

        val call = api.getCoachConnections(student.studentID)
        call.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
              textView.text = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })
    }
}
