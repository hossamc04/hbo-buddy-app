package com.example.hbo_buddy_app

import android.accounts.AccountManager
import android.accounts.OnAccountsUpdateListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.hbo_buddy_app.authenticator.AuthenticatorActivity
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.chat.ChatActivity
import com.example.hbo_buddy_app.edit_profile.EditProfileActivity
import com.example.hbo_buddy_app.models.*
import com.example.hbo_buddy_app.retrofit.RetroFitService
import com.example.hbo_buddy_app.select_buddy.SelectBuddyActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val madeconnection : Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val am = AccountManager.get(this)
        val accounts = am.getAccountsByType("inholland_buddy_app")

        //if not logged in redirect to login page
        if (accounts.isEmpty()){
            val intent = Intent(this , AuthenticatorActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
            finish()
            return
        }

        //if hbo account redirect to hbo page
        val accountType : Int = am.getUserData(accounts[0], "student_type").toInt()
        if (accountType == 3){
             val intent = Intent(this, MainHBOActivity::class.java)
            ContextCompat.startActivity(this,intent,null)
            finish()
            return
        }

        //check if coachconnection is made



        toBuddySelection.setOnClickListener {
            val intent = Intent(this, SelectBuddyActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
        }

        toChat.setOnClickListener {
            val intent = Intent(this, ChatActivity:: class.java)
            ContextCompat.startActivity(this, intent, null )
        }


        fun goToEditActivity(student: Student){
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("profile", student)
            ContextCompat.startActivity(this, intent, null)
        }



        toEditProfile.setOnClickListener {
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


                            retrofitService2.getTutorantProfileById(studentId).enqueue(
                                object : Callback<TutorantProfile> {
                                    override fun onFailure(call: Call<TutorantProfile>, t: Throwable) {
                                        Log.d("a", "b")
                                    }
                                    override fun onResponse(call: Call<TutorantProfile>, response: Response<TutorantProfile>) {
                                        if (response.isSuccessful && response.code() == 200) {

                                            goToEditActivity(response.body()!!.student)

                                        }
                                        else if (response.code() == 404) {
                                            //setButtonMake()
                                        }
                                    }
                                })
                            }
                         }
                     })
        }





        //check for profile

    }
}













 /*                               else if (response.isSuccessful && response.code() !=  200){
                                    //if not found api returns 500, i dont know why
                                    Log.d("profile", "${response.code()}")
                                    retrofitService2.getcoachrofileById(studentId).enqueue(object : Callback<CoachProfile>{
                                        override fun onFailure(
                                            call: Call<CoachProfile>,
                                            t: Throwable
                                        ) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onResponse(
                                            call: Call<CoachProfile>,
                                            response: Response<CoachProfile>
                                        ) {
                                            if (response.isSuccessful && response.code() == 200){

                                            }

                                            else if (response.isSuccessful && response.code() == 500){
                                                // if not found api returns 500 dont know why
                                                Log.d("error", "no profiles found, make one")

                                            }
                                        }

                                    })
                                }

                            }
                        }
                    )*/




/*

                }
            }
        })
*/







/*        toBuddySelection.setOnClickListener{
            val intent = Intent(this, SelectBuddyActivity::class.java)
            ContextCompat.startActivity(this,intent, null)
        }*/
        /*
        toChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            ContextCompat.startActivity(this,intent, null)
        }*/
    //}



    /*private fun makeRequest(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-tinderfunv2.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: ApiService = retrofit.create(ApiService::class.java)
        val call = api.getShit()


        call.enqueue(object: Callback<Student> {
            override fun onFailure(call: Call<Student>, t: Throwable) {
                text.text = "Connection failed, try again later"
            }

            override fun onResponse(call: Call<Student>, response: Response<Student>) {

                recycler.layoutManager = LinearLayoutManager(this@MainActivity)


                val list = response.body()
                text.visibility = View.GONE;


                val clickInterface:ClickListener = object:ClickListener{
                    override fun onClick(position: Int) {
                        val intent: Intent = Intent(this@MainActivity, Detail::class.java)
                        //intent.putExtra(INTENT_KEY_1, list!!.articles[position].Id)
                        intent.putExtra(AUTHTOKEN, auth)
                        startActivity(intent)
                    }
                }
                recycler.adapter = Adapter(
                    this@MainActivity,
                    list!!.articles,
                    clickInterface
                )
                //recycler.adapter.notifyDataSetChanged()
            }
        })
    }*/
//}
