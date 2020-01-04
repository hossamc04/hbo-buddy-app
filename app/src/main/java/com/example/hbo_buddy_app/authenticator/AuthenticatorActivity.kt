package com.example.hbo_buddy_app.authenticator

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.hbo_buddy_app.MainActivity
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.dagger.activity_components.DaggerAuthenticatorActivityComponent
import com.example.hbo_buddy_app.databinding.ActivityAuthenticatorBinding
import javax.inject.Inject

class AuthenticatorActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewmodel
        DaggerAuthenticatorActivityComponent.create().inject(this)
        viewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(LoginViewModel::class.java)


        //databinding
        val bind : ActivityAuthenticatorBinding = DataBindingUtil.setContentView(this, R.layout.activity_authenticator)
        bind.lifecycleOwner = this
        bind.viewModel = viewModel

        //observe result
        viewModel.login().observe(this, Observer{
            if (it != null){
                val am = AccountManager.get(this)
                val acc = Account(viewModel.userName, "inholland_buddy_app")

                val extraData = Bundle()
                extraData.putString("student_type", it)

                am.addAccountExplicitly(acc, viewModel.passWord, extraData)

                val intent = Intent(this , MainActivity::class.java)
                ContextCompat.startActivity(this, intent, null)
                finish()
            }
        })

        val maakNieuwButton : TextView = findViewById(R.id.maak_nieuw_button)

        maakNieuwButton.setOnClickListener{
            val intent = Intent(this , MakeNewAccountActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
            finish()
        }


    }
}
