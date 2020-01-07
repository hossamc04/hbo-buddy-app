package com.example.hbo_buddy_app.select_buddy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.models.CoachProfile
import kotlinx.android.synthetic.main.activity_edit_profile.*


class BuddyProfileRecyclerViewAdapter(val act: SelectBuddyActivity) : RecyclerView.Adapter<BuddyProfileRecyclerViewAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.coach_name)
        val description: TextView = itemView.findViewById(R.id.coach_bio)
        val interests : TextView = itemView.findViewById(R.id.interests)
        val study : TextView = itemView.findViewById(R.id.coach_study)
        val studyYear : TextView = itemView.findViewById(R.id.study_year)
        val image : TextView = itemView.findViewById(R.id.coach_image)
        val readmore : TextView = itemView.findViewById(R.id.read_more)
        //collapsable
        val collapsable : RelativeLayout  = itemView.findViewById(R.id.collapsable)
        val button : Button = itemView.findViewById(R.id.selecteerBuddyButton)
        val readLess : TextView = itemView.findViewById(R.id.read_less)



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


        holder.name.text = "${coach.firstName} ${coach.surName}"
        holder.study.text = "Studie: ${myDataset[position].student.study}"
        holder.studyYear.text = "StudieJaar: ${myDataset[position].student.studyYear.toString()}"
        holder.interests.text = "Interesses: ${myDataset[position].student.interests}"

        //holder.description.text = coach.description
        holder.description.text = "Bio: ${coach.description}"
        holder.button.text = "selecteer ${coach.firstName}"

        if (myDataset[position].student.photo != ""){
/*            try{
                Glide.with(act).load(myDataset[position].student.photo).into(image)}
            catch (e: Exception){
                Log.d("fout", "${e}")

            }*/


        }


        if (myDataset[position].isVisable){
            holder.collapsable.visibility = View.VISIBLE
            holder.readmore.visibility = View.GONE
        }
        else{
            holder.collapsable.visibility = View.GONE
            holder.readmore.visibility = View.VISIBLE
        }

        holder.readmore.setOnClickListener{
            myDataset[position].isVisable = true
            notifyDataSetChanged()
        }

        holder.readLess.setOnClickListener{
            myDataset[position].isVisable = false
            notifyDataSetChanged()
        }

        holder.button.setOnClickListener {
            act.makeConnection(myDataset[position].student.studentID);
        }
    }

    override fun getItemCount() = myDataset.size

    fun addItems(coachList: ArrayList<CoachProfile>) {
        myDataset.addAll(coachList)
        notifyDataSetChanged()
    }
}
