package me.kevcar.scaffolding.api.datasource

import io.reactivex.Observable
import me.kevcar.scaffolding.core.entity.ImagePage
import me.kevcar.scaffolding.data.api.service.ImageService
import me.kevcar.scaffolding.domain.datasource.RemoteImageDataSource


class RetrofitImageDataSource(private val service: ImageService) : RemoteImageDataSource {
    override fun fetchImages(query: String, pageSize: Int, pageNumber: Int): Observable<ImagePage> {
        return service.search(query, pageSize, pageNumber)
                .map {
                    ImagePage(query, pageSize, it.images.map { it.toCore() }, pageNumber)
                }
    }
}
