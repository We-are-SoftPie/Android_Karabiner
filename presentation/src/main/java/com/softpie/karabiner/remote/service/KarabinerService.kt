package com.softpie.karabiner.remote.service

import com.softpie.karabiner.remote.response.BaseResponse
import com.softpie.karabiner.remote.response.karabiner.ImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface KarabinerService {

    @Multipart
    @POST("/img")
    suspend fun postImage(
        @Part image: MultipartBody.Part
    ): BaseResponse<ImageResponse>
}