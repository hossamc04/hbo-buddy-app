package com.example.hbo_buddy_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun makeRequest(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev-tinderfunv2.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: ApiService = retrofit.create(ApiService::class.java)
        val call = api.getShit()


        call.enqueue(object: Callback<ArticleList> {
            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                text.text = "Connection failed, try again later"
            }

            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {

                recycler.layoutManager = LinearLayoutManager(this@MainActivity)


                val list = response.body()
                text.visibility = View.GONE;


                val clickInterface:ClickListener = object:ClickListener{
                    override fun onClick(position: Int) {
                        val intent: Intent = Intent(this@MainActivity, Detail::class.java)
                        intent.putExtra(INTENT_KEY_1, list!!.articles[position].Id)
                        intent.putExtra(AUTHTOKEN, auth)
                        startActivity(intent)
                    }
                }
                recycler.adapter = Adapter(this@MainActivity, list!!.articles, clickInterface)
                //recycler.adapter.notifyDataSetChanged()
            }
        })
    }
}
