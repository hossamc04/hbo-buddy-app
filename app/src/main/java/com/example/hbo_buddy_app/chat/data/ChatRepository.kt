package com.example.hbo_buddy_app.chat.data

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.example.hbo_buddy_app.models.Message
import com.example.hbo_buddy_app.models.SendMessage
import com.example.hbo_buddy_app.retrofit.RetroFitService

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChatRepository @Inject constructor(
    var retrofitService : RetroFitService)
{

    private fun stringToDate(dateString: String): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return sdf.parse(dateString)
    }

    private val alreadyPosted = ArrayList<Message>()
    private fun clearArrayOfDouble(messages: ArrayList<Message>): ArrayList<Message> {
        val newArrayList = ArrayList<Message>()
        messages.forEach {
            if (!alreadyPosted.contains(it)){
                alreadyPosted.add(it)
                newArrayList.add(it)
            }
        }
        return newArrayList
    }


    fun sendMessage(sendMessage: SendMessage){
/*        retrofitService.sendMessage(sendMessage).enqueue(object: Callback<Any>{
            override fun onFailure(call: Call<Any>, t: Throwable) {
                //do nothing
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                //do nothing
            }

        })*/
    }

    fun getMessagesLiveData(): MutableLiveData<ArrayList<Message>> {
        val mutableLiveData = MutableLiveData<ArrayList<Message>>()
        val mainHandler = Handler(Looper.getMainLooper())
        /*mainHandler.post(object : Runnable {
            override fun run() {
                retrofitService.getConversationBetweenBuddyAndTut(123456, 590873).enqueue(object : Callback<ArrayList<Message>> {
                    override fun onFailure(call: Call<ArrayList<Message>>, t: Throwable) {
                        Log.d("CallFailure: ", t.message)
                    }

                    override fun onResponse(call: Call<ArrayList<Message>>, response: Response<ArrayList<Message>>){
                        //Log.d("size", "${size.toString()} || ${response.body()!!.size}")
                        if (response.isSuccessful ){
                            Log.d("date", "${response.body()!![0].created}")

                            mutableLiveData.value = clearArrayOfDouble(response.body()!!)
                        }
                    }
                })

                mainHandler.postDelayed(this, 1000)
            }
        })*/


        return mutableLiveData
    }
}