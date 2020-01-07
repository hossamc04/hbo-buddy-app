package com.example.hbo_buddy_app.authenticator

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenHeaderInterceptor2(val token : String, val studentNummer : String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {

        proceed(
            request()
                .newBuilder()
                .addHeader("AuthId", studentNummer)
                .addHeader("AuthToken", token)
                .build()
        )
    }
}