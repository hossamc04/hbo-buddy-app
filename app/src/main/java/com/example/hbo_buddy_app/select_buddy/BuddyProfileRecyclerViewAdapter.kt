package com.example.hbo_buddy_app.select_buddy

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.hbo_buddy_app.MainActivity
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.authenticator.AuthenticatorActivity
import com.example.hbo_buddy_app.models.CoachProfile
import kotlinx.android.synthetic.main.activity_edit_profile.*


class BuddyProfileRecyclerViewAdapter(val act: SelectBuddyActivity) : RecyclerView.Adapter<BuddyProfileRecyclerViewAdapter.MyViewHolder>() {


    val options : RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.emptyprofile)
        .error(R.drawable.emptyprofile)


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.coach_name)
        val description: TextView = itemView.findViewById(R.id.coach_bio)
        val interests : TextView = itemView.findViewById(R.id.interests)
        val study : TextView = itemView.findViewById(R.id.coach_study)
        val studyYear : TextView = itemView.findViewById(R.id.study_year)
        val image : ImageView = itemView.findViewById(R.id.coach_image)
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

        val imageUrl = myDataset[position]!!.student.photo
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .apply(options)
            .into(holder.image)

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
            act.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    }

    override fun getItemCount() = myDataset.size

    fun addItems(coachList: ArrayList<CoachProfile>) {
        myDataset.addAll(coachList)
        notifyDataSetChanged()
    }

    fun unlock(){
        act.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun close(){
        val intent = Intent(act.applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        ContextCompat.startActivity(act.applicationContext, intent, null)
    }
}

