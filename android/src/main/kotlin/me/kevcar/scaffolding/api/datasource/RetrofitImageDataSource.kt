package me.kevcar.scaffolding.api.datasource

import io.reactivex.Single
import me.kevcar.scaffolding.api.response.ImageResponse
import me.kevcar.scaffolding.api.service.ImageService
import me.kevcar.scaffolding.core.entity.Image
import me.kevcar.scaffolding.domain.datasource.RemoteImageDataSource


class RetrofitImageDataSource(private val service: ImageService) : RemoteImageDataSource {
    override fun fetchImages(query: String, pageSize: Int, offset: Int): Single<List<Image>> {
        return service.search(query, pageSize, offset)
                .map(ImageResponse::images)
    }
}
