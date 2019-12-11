package com.example.hbo_buddy_app.select_buddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.dagger.DaggerSelectBuddyActivityComponent
import kotlinx.android.synthetic.main.activity_select_buddy.*
import javax.inject.Inject

class SelectBuddyActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SelectBuddyViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_buddy)


        DaggerSelectBuddyActivityComponent.create().inject(this)
        viewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(SelectBuddyViewModel::class.java)

        //setup recyclerview
        buddyProfileRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = BuddyProfileRecyclerViewAdapter(this)
        buddyProfileRecyclerView.adapter = adapter

        //observe changes
        viewModel.getAllCoaches().observe(this, Observer {
            Log.d("SIZE", it.size.toString())
            adapter.addItems(it)
        })
    }


}
