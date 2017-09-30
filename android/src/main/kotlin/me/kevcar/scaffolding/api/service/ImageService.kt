package me.kevcar.scaffolding.api.service

import me.kevcar.scaffolding.api.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageService {

    @GET("search")
    fun search(
            @Query("q") query: String,
            @Query("count") pageSize: Int,
            @Query("offset") offset: Int) : Response<ImageResponse>
}
