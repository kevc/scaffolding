package me.kevcar.scaffolding.api.service

import io.reactivex.Observable
import me.kevcar.scaffolding.api.response.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageService {

    @GET("search")
    fun search(
            @Query("q") query: String,
            @Query("count") pageSize: Int,
            @Query("offset") offset: Int) : Observable<ImageResponse>
}
