package com.example.hbo_buddy_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hbo_buddy_app.models.CoachTutorantConnection
import com.example.hbo_buddy_app.models.Student
import com.example.hbo_buddy_app.models.Tutorant
import kotlinx.android.synthetic.main.chat_list_item.view.*

class ChatListAdapter(val context: Context, val list: MutableList<Student>, val listener: ClickListener) : RecyclerView.Adapter<ChatListAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name = itemView.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = list.get(position).firstName
        holder.itemView.setOnClickListener{
            listener.onItemClick(holder.adapterPosition)
        }
    }
}