package com.example.hbo_buddy_app.dagger

import com.example.hbo_buddy_app.api.ApiConnection
import com.example.hbo_buddy_app.api.ApiService
import dagger.Module
import dagger.Provides

@Module
class APIConnectionModule{
    @Provides
    fun provideApiConnection(): ApiService {
        return ApiConnection.instance
    }
}
