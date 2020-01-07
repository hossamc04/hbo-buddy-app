package com.example.hbo_buddy_app.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.models.CoachProfile
import com.example.hbo_buddy_app.models.Message


class ChatRecyclerViewAdapter() : RecyclerView.Adapter<ChatRecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val message : TextView = itemView.findViewById(R.id.message)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)

        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = myDataset[position]
        holder.message.text = message.payload
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    fun addItems(messageList: ArrayList<Message>) {
        messageList.forEach {
            if (!myDataset.any { message -> message.messageID == it.messageID }){
                myDataset.add(it)
                notifyDataSetChanged()
            }
        }



    }
    companion object{
        private val myDataset: ArrayList<Message> = ArrayList()
    }

}
