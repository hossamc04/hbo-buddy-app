package com.example.hbo_buddy_app

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.OnAccountsUpdateListener
import android.app.Application
import android.content.Intent
import android.util.Log
import com.example.hbo_buddy_app.authenticator.AuthenticatorActivity


class Initial : Application(), OnAccountsUpdateListener {

    override fun onCreate() {
        super.onCreate()
        val am = AccountManager.get(applicationContext)
        am.addOnAccountsUpdatedListener(this, null, false)
    }

    override fun onAccountsUpdated( accounts: Array<out Account>?) {
        Log.d("test","test")
        // logout


        if (accounts != null) {
            var newAccount: Account? = null
            for (account in accounts) {
                if (account.type == "inholland_buddy_app") {
                    newAccount = account
                }
            }
            // logout
            if (newAccount == null) {
                Log.d("account: ", "account removed")

                val intent = Intent(applicationContext, AuthenticatorActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)

            }
            //login

        }
    }
}