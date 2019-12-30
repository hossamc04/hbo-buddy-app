package com.example.hbo_buddy_app.authenticator

import android.app.Service
import android.content.Intent
import android.os.IBinder


class AuthenticatorService : Service() {
    private var mAuthenticator: Authenticator? = null

    override fun onCreate() {
        mAuthenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent): IBinder {
        return mAuthenticator!!.iBinder
    }
}