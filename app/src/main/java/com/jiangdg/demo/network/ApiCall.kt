package com.jiangdg.demo.network

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiCall {
    @Multipart
    @POST("https://quantrung94.free.beeceptor.com/todos")
    fun uploadImage(@Part image: MultipartBody.Part): Call<ApiCallResponse>
}