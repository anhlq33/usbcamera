package com.jiangdg.demo.network

import com.google.gson.internal.GsonBuildConfig
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.io.File

object ApiCallService {
    private val BASE_URL = "http://demo2842623.mockable.io/"
    val okhttp2Client = OkHttpClient.Builder()
    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okhttp2Client.addInterceptor(logging)
    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp2Client.build())
        .build()
        .create(ApiCall::class.java)

    private fun prepareImg(fileNameVsPath: String?) : Call<ApiCallResponse> {
//        val imageFile = File("/storage/emulated/0/Download/test.png")
        val imageFile = File(fileNameVsPath)

// Tạo MultipartBody.Part từ file hình ảnh
        val requestFile = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

        return api.uploadImage(imagePart)
    }
    fun call(fileNameVsPath: String?) = prepareImg(fileNameVsPath)
}