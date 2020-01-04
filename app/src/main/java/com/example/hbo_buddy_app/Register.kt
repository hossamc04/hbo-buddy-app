package com.example.hbo_buddy_app

import java.util.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hbo_buddy_app.models.DefaultResponse
import com.example.hbo_buddy_app.models.RegisterModel
import com.example.hbo_buddy_app.retrofit.RetroFitService
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerBtn.setOnClickListener{
            loginStudent()
        }
    }
    private fun loginStudent(){
        val string = studentID.text.toString()
        val studentnr = string.toInt()
        val pass = password.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:7071")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: RetroFitService = retrofit.create(RetroFitService::class.java)

        //val register = RegisterModel(12, "gfhddghhfg",studentnr, pass, 3)

        //val call = api.registerStudent(studentnr, pass, 3)
        //val call = api.registerStudent(register)

        //call.enqueue(object: Callback<DefaultResponse> {
          //  override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
          //      textView.text = t.message
          //  }

           // override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                //val intent: Intent = Intent(this@Register, MainActivity::class.java)
            //}
        //})

    }
}
