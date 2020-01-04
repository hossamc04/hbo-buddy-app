package com.example.hbo_buddy_app.authenticator

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenHeaderInterceptor(val token : String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {

        proceed(
            request()
                .newBuilder()
                .addHeader("AuthId", "581433")
                .addHeader("AuthToken", token)
                .build()
        )
    }
}