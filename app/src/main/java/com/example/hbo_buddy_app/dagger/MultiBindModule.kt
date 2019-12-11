package com.example.hbo_buddy_app.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(viewModel: ChatViewModel) : ViewModel




/*
    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel) : ViewModel
*/


}