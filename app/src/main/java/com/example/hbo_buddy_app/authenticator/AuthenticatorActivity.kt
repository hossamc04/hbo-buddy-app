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
import com.example.hbo_buddy_app.MainHBOActivity
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.dagger.activity_components.DaggerAuthenticatorActivityComponent
import kotlinx.android.synthetic.main.activity_authenticator.*
import javax.inject.Inject

class AuthenticatorActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticator)

        val am = AccountManager.get(this)
        if(am.getAccountsByType("inholland_buddy_app").size > 1){
            val intent = Intent(this , SignOutFirstActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
            finish()
        }

        //viewmodel
        DaggerAuthenticatorActivityComponent.create().inject(this)
        viewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(LoginViewModel::class.java)


        val password : String = "";
        val username: String = "";


        button_login.setOnClickListener {
            viewModel.login(gebruikersnaam.text.toString(), wachtwoord.text.toString()).observe(this, Observer{
                if (it != null){

                    val acc = Account(gebruikersnaam.text.toString(), "inholland_buddy_app")

                    val extraData = Bundle()
                    extraData.putString("student_type", it)
                    am.addAccountExplicitly(acc, wachtwoord.text.toString(), extraData)

                    if (it == "3"){
                        val intent = Intent(this , MainHBOActivity::class.java)
                        ContextCompat.startActivity(this, intent, null)
                        finish()

                    }

                    if (it == "4"){
                        val intent = Intent(this , MainActivity::class.java)
                        ContextCompat.startActivity(this, intent, null)
                        finish()

                    }

                }
            })



        }




        maak_nieuw_button.setOnClickListener{
            val intent = Intent(this , MakeNewAccountActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
            finish()
        }


    }
}
