package me.kevcar.scaffolding.api.datasource

import io.reactivex.Observable
import io.reactivex.Single
import me.kevcar.scaffolding.api.response.ImageResponse
import me.kevcar.scaffolding.api.service.ImageService
import me.kevcar.scaffolding.core.entity.Image
import me.kevcar.scaffolding.core.entity.ImagePage
import me.kevcar.scaffolding.domain.datasource.RemoteImageDataSource


class RetrofitImageDataSource(private val service: ImageService) : RemoteImageDataSource {
    override fun fetchImages(query: String, pageSize: Int, offset: Int): Observable<ImagePage> {
        return service.search(query, pageSize, offset)
                .map {
                    ImagePage(query, pageSize, it.images, it.nextPageOffset)
                }
    }
}
