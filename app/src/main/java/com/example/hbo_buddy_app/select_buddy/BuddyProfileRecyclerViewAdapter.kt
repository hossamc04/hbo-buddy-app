package com.example.hbo_buddy_app.select_buddy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.models.CoachProfile



class BuddyProfileRecyclerViewAdapter(val act: SelectBuddyActivity) : RecyclerView.Adapter<BuddyProfileRecyclerViewAdapter.MyViewHolder>() {



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.coach_name)
        val description: TextView = itemView.findViewById(R.id.coach_bio)
        //val description: itemView.findViewById(R.)
        val button : Button = itemView.findViewById(R.id.selecteerBuddyButton)
        val readmore : TextView = itemView.findViewById(R.id.read_more)
        val collapsable : RelativeLayout  = itemView.findViewById(R.id.collapsable)

        init {
            //itemView.

        }



    }

    var myDataset: ArrayList<CoachProfile> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coachprofile, parent, false)

        return MyViewHolder(textView)
    }



    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val coach = myDataset[position].student

        val firstPlusLastName = "${coach.firstName} ${coach.surName}"
        holder.name.text = firstPlusLastName
        //holder.description.text = coach.description
        holder.description.text = "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
        holder.button.text = "selecteer ${coach.firstName}"

        if (myDataset[position].isVisable){
            holder.collapsable.visibility = View.VISIBLE
        }
        else{
            holder.collapsable.visibility = View.GONE
        }

        holder.readmore.setOnClickListener{
            myDataset[position].isVisable = true
            notifyDataSetChanged()

        }
    }

    override fun getItemCount() = myDataset.size

    fun addItems(coachList: ArrayList<CoachProfile>) {
        myDataset.addAll(coachList)
        notifyDataSetChanged()
    }
}
