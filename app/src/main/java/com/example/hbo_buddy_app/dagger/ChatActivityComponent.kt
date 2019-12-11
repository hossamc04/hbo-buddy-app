package com.example.hbo_buddy_app.dagger

import com.example.hbo_buddy_app.chat.ChatActivity
import com.example.hbo_buddy_app.select_buddy.SelectBuddyActivity
import dagger.Component


    @Component(modules = [MultiBindModule::class, APIConnectionModule::class])
    interface chatActivityComponent {
        fun inject (activity: ChatActivity)
    }

