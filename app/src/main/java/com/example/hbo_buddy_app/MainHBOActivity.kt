package com.example.hbo_buddy_app

import android.accounts.AccountManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.chat.ChatActivity
import com.example.hbo_buddy_app.edit_profile.EditProfileActivity
import com.example.hbo_buddy_app.models.*
import com.example.hbo_buddy_app.retrofit.RetroFitService
import kotlinx.android.synthetic.main.activity_main_hbo.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class MainHBOActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("Home")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hbo)

        val am = AccountManager.get(this)

        chatBtn.setOnClickListener{
            val intent = Intent(this, ChatListActivity:: class.java)
            ContextCompat.startActivity(this, intent, null )
        }

        editProfileBtn.setOnClickListener{
            editProfile(am)
        }




        //val intent: Intent = getIntent()
        //val student: Student = intent.getParcelableExtra("profile")


        //check if someone matched

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-tinderclonefa-test.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: RetroFitService = retrofit.create(RetroFitService::class.java)

        //val call = api.getCoachTutorantConnection(student.studentID)
/*        call.enqueue(object: Callback<CoachTutorantConnection> {
            override fun onFailure(call: Call<CoachTutorantConnection>, t: Throwable) {
              //textView.text = t.message
            }

            override fun onResponse(call: Call<CoachTutorantConnection>, response: Response<CoachTutorantConnection>) {

            }
        })*/
    }

    fun editProfile(am: AccountManager){
        val accounts = am.getAccountsByType("inholland_buddy_app")
        val studentId = accounts[0].name
        val accountType : Int = am.getUserData(accounts[0], "student_type").toInt()

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

                    retrofitService2.getCoachProfileById(studentId).enqueue(
                        object : Callback<CoachProfile> {
                            override fun onFailure(call: Call<CoachProfile>, t: Throwable) {
                                Log.d("a", "b")
                            }
                            override fun onResponse(call: Call<CoachProfile>, response: Response<CoachProfile>) {
                                if (response.isSuccessful && response.code() == 200) {
                                    val intent = Intent(this@MainHBOActivity, EditProfileActivity::class.java)
                                    intent.putExtra("profile", response.body()!!.student)
                                    ContextCompat.startActivity(this@MainHBOActivity, intent, null)
                                }
                                else if (response.code() == 404) {
                                    //setButtonMake()
                                }
                            }
                        })



//                    retrofitService2.getTutorantProfileById(studentId).enqueue(
//                        object : Callback<TutorantProfile> {
//                            override fun onFailure(call: Call<TutorantProfile>, t: Throwable) {
//                                Log.d("a", "b")
//                            }
//                            override fun onResponse(call: Call<TutorantProfile>, response: Response<TutorantProfile>) {
//                                if (response.isSuccessful && response.code() == 200) {
//                                    val intent = Intent(this@MainHBOActivity, EditProfileActivity::class.java)
//                                    intent.putExtra("profile", response.body()!!.student)
//                                    ContextCompat.startActivity(this@MainHBOActivity, intent, null)
//                                }
//                                else if (response.code() == 404) {
//                                    //setButtonMake()
//                                }
//                            }
//                        })
                }
            }
        })

    }
}
