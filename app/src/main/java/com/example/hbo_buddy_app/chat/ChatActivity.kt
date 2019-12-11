package com.example.hbo_buddy_app.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.dagger.DaggerchatActivityComponent
import com.example.hbo_buddy_app.select_buddy.BuddyProfileRecyclerViewAdapter
import com.example.hbo_buddy_app.select_buddy.SelectBuddyViewModel
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_select_buddy.*

import javax.inject.Inject

class ChatActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ChatViewModel
    val adapter = ChatRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //viewmodel
        DaggerchatActivityComponent.create().inject(this)
        viewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(ChatViewModel::class.java)

        //setuprecyclerview
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = adapter

        //observe
        viewModel.getAllMesages().observe(this, Observer {
            adapter.addItems(it)
        })










        buttonSendMessage.setOnClickListener {
            val input = editTextMessage.text.toString()
            viewModel.onSendMessage(input)
        }
    }
}
