package com.example.hbo_buddy_app

import android.accounts.AccountManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.hbo_buddy_app.authenticator.AuthenticatorActivity
import com.example.hbo_buddy_app.chat.ChatActivity
import com.example.hbo_buddy_app.select_buddy.SelectBuddyActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

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
        }





/*        toBuddySelection.setOnClickListener{
            val intent = Intent(this, SelectBuddyActivity::class.java)
            ContextCompat.startActivity(this,intent, null)
        }*/
        /*
        toChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            ContextCompat.startActivity(this,intent, null)
        }*/
    }



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
}
