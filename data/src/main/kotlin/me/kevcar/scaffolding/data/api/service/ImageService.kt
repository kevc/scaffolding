package me.kevcar.scaffolding.data.api.service

import io.reactivex.Observable
import me.kevcar.scaffolding.data.api.response.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit: http://square.github.io/retrofit/

interface ImageService {

    @GET("search/images")
    fun search(/* TODO */) : Observable<ImageResponse>
}
