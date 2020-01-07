package com.example.hbo_buddy_app.select_buddy

import android.accounts.Account
import android.accounts.AccountManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.dagger.activity_components.DaggerSelectBuddyActivityComponent
import com.example.hbo_buddy_app.models.CoachProfile
import kotlinx.android.synthetic.main.activity_select_buddy.*
import javax.inject.Inject

class SelectBuddyActivity : AppCompatActivity() {

    @Inject
    lateinit var daggerViewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SelectBuddyViewModel

    val recyclerViewAdapter = BuddyProfileRecyclerViewAdapter(this)

    fun makeConnection(buddyId: String){
        val am = AccountManager.get(this)
        val userid : String  = am.accounts[0].name
        val acc : Account = am.accounts[0]
        val pass = am.getPassword(acc)
        Log.d("pass", "${pass}")
        viewModel.onBuddySelect(userid, buddyId,pass)


    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_buddy)

        if (savedInstanceState != null  ){
            var articleList : ArrayList<CoachProfile>? = savedInstanceState.getParcelableArrayList("ARTICLE_LIST")
            if (articleList != null ){
                recyclerViewAdapter.myDataset = articleList
            }
        }


        DaggerSelectBuddyActivityComponent.create().inject(this)
        viewModel = ViewModelProviders.of(this, daggerViewModelFactory).get(SelectBuddyViewModel::class.java)

        //setup recyclerview
        buddyProfileRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = recyclerViewAdapter
        buddyProfileRecyclerView.adapter = adapter



        //observe changes
        viewModel.getAllCoaches().observe(this, Observer {
            Log.d("SIZE", it.size.toString())
            recyclerViewAdapter.addItems(it)
        })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putParcelableArrayList("ARTICLE_LIST", recyclerViewAdapter.myDataset)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    //older api
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("ARTICLE_LIST", recyclerViewAdapter.myDataset)
        super.onSaveInstanceState(outState)
    }
}
