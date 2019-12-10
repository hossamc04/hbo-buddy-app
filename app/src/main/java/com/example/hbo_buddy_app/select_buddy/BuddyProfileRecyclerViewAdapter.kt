package com.example.hbo_buddy_app.select_buddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.models.CoachProfile



class BuddyProfileRecyclerViewAdapter(val act: SelectBuddyActivity) : RecyclerView.Adapter<BuddyProfileRecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        //val description: itemView.findViewById(R.)
        val button : Button = itemView.findViewById(R.id.selecteerBuddyButton)
    }

    private val myDataset: ArrayList<CoachProfile> = ArrayList()

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
        holder.button.setOnClickListener {
            act.viewModel.onBuddySelect(coach.studentID)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    fun addItems(coachList: ArrayList<CoachProfile>) {
        myDataset.addAll(coachList)
        notifyDataSetChanged()
    }
}
