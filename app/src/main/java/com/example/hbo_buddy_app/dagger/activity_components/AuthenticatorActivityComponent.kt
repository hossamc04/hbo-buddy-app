package com.example.hbo_buddy_app.dagger.activity_components

import com.example.hbo_buddy_app.authenticator.AuthenticatorActivity
import com.example.hbo_buddy_app.dagger.APIConnectionModule
import com.example.hbo_buddy_app.dagger.view_models.MultiBindModule
import dagger.Component


@Component(modules = [MultiBindModule::class, APIConnectionModule::class])
interface AuthenticatorActivityComponent {
    fun inject(activity: AuthenticatorActivity)
}