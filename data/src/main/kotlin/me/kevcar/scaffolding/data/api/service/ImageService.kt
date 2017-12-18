package me.kevcar.scaffolding.data.api.service

import io.reactivex.Observable
import me.kevcar.scaffolding.data.api.response.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("search/images")
    fun search(
            @Query("phrase") query: String,
            @Query("page_size") pageSize: Int,
            @Query("page") offset: Int) : Observable<ImageResponse>
}
