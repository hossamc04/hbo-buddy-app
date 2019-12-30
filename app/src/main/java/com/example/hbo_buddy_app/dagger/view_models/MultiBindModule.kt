package com.example.hbo_buddy_app.dagger.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hbo_buddy_app.authenticator.LoginViewModel
import com.example.hbo_buddy_app.chat.ChatViewModel
import com.example.hbo_buddy_app.select_buddy.SelectBuddyViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class MultiBindModule{
    @Binds
    abstract fun bindsViewModelFactory(factory : DaggerViewModelFactory): ViewModelProvider.Factory


    // Add ViewModelBinds here
    @Binds
    @IntoMap
    @ViewModelKey(SelectBuddyViewModel::class)
    abstract fun bindSelectBuddyViewModel(viewModel: SelectBuddyViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(viewModel: ChatViewModel) : ViewModel




}