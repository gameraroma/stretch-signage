package com.example.chakpiwat.stretchsignage

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface LoadVideoService {
    @Streaming
    @GET("/v0/b/firestrore-practice.appspot.com/o/bp-video%2Flisa-2.mp4?alt=media&token=07aa0a40-b0ef-4c74-81e6-45e07d220916")
    fun downloadFileWithFixedUrl(): Call<ResponseBody>

    @Streaming
    @GET
    fun downloadFileWithDynamicUrlSync(@Url fileUrl: String): Call<ResponseBody>
}