package com.example.hbo_buddy_app.edit_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.models.*
import com.example.hbo_buddy_app.retrofit.RetroFitService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val intent: Intent = getIntent()
        val profile: Student = intent.getParcelableExtra("profile")

        val voornaam: EditText = findViewById(R.id.edit_first_name)
        voornaam.setText(profile.firstName)
        val achternaam: EditText = findViewById(R.id.edit_last_name)
        achternaam.setText(profile.surName)
        val degree: EditText = findViewById(R.id.edit_degree)
        degree.setText(profile.degree)
        val description: EditText = findViewById(R.id.edit_description)
        description.setText(profile.description)
        val phoneNumber: EditText = findViewById(R.id.edit_phone_number)
        phoneNumber.setText(profile.phoneNumber)
        val interests: EditText = findViewById(R.id.edit_interest)
        interests.setText(profile.interests)

        val study: EditText = findViewById(R.id.edit_study)
        study.setText(profile.study)
        val studyyear: EditText = findViewById(R.id.edit_study_year)
        studyyear.setText(profile.studyYear.toString())
        val button: Button = findViewById(R.id.edit_profile_button)

        button.setOnClickListener {
            val voornaam: String = voornaam.text.toString()
            val achternaam: String = achternaam.text.toString()
            val degree: String = degree.text.toString()
            val description: String = description.text.toString()
            val phoneNumber: String = phoneNumber.text.toString()
            val interests: String = interests.text.toString()
            val study: String = study.text.toString()
            val studyyear: String = studyyear.text.toString()

            val profile2 = Student(degree, description, voornaam, interests, phoneNumber, "", profile.studentID, study, studyyear.toInt(), achternaam)


            val baseUrl = "https://dev-tinderclonefa-test.azurewebsites.net/api/"

            val okHttpClient: OkHttpClient = OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build()


            val retroFitService = Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetroFitService::class.java)


            retroFitService.login(LoginModel("581433", "test")).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Log.d("AdminToken", "${response.body()}")
                        val okHttpClient2 = OkHttpClient()
                            .newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(TokenHeaderInterceptor(response.body()!!))
                            .writeTimeout(60, TimeUnit.SECONDS).build()

                        val retrofitService2 = Retrofit
                            .Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient2)
                            .build()
                            .create(RetroFitService::class.java)

                        retrofitService2.editProfile(profile.studentID, profile2).enqueue(object: Callback<ResponseBody>{
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Log.d("sss", "ss")
                            }

                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if (response.isSuccessful){
                                    Log.d("sss", "${response.message()}")
                                }

                                else{
                                    Log.d("ss","${response.message()}")
                                    profile.toString()
                                }
                            }

                        })
                    }
                }
            })
        }



    }
}








