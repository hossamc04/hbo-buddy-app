package com.example.hbo_buddy_app.dagger


import com.example.hbo_buddy_app.retrofit.RetroFitService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
class APIConnectionModule{
    @Provides
    fun provideApiConnection(): RetroFitService {
        val baseUrl = "https://dev-tinderclonefa-test.azurewebsites.net/api/"

        val  okHttpClient: OkHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build()

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetroFitService::class.java)
    }
}

