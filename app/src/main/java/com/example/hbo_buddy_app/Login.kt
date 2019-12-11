package com.example.hbo_buddy_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hbo_buddy_app.api.ApiService
import com.example.hbo_buddy_app.models.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        registerBtn.setOnClickListener{
            loginUser()
        }
    }
    private fun loginUser(){
        val string = studentID.text.toString()
        val studentnr = string.toInt()
        val pass = password.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:7071")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: ApiService = retrofit.create(ApiService::class.java)
        //var json = JsonObject()
        val login = LoginModel(studentnr, pass)

        val call = api.loginStudent(login)

        call.enqueue(object: Callback<UserAuth> {
            override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                textView.text = t.message
            }

            override fun onResponse(call: Call<UserAuth>, response: Response<UserAuth>) {
                //val intent: Intent = Intent(this@Register, MainActivity::class.java)
            }
        })

    }
}
