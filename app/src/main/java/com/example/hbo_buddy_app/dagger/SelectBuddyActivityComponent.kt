package com.example.hbo_buddy_app.dagger

import com.example.hbo_buddy_app.select_buddy.SelectBuddyActivity
import dagger.Component

@Component(modules = [MultiBindModule::class, APIConnectionModule::class])
interface SelectBuddyActivityComponent {
    fun inject (activity: SelectBuddyActivity)
}
