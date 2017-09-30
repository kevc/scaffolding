package me.kevcar.scaffolding.domain.interactor.image

import io.reactivex.Single
import me.kevcar.scaffolding.core.entity.Image
import me.kevcar.scaffolding.domain.datasource.RemoteImageDataSource
import javax.inject.Inject


class FetchImages @Inject constructor(private val remote: RemoteImageDataSource) {

    fun execute(request: Request): Single<Response> {
        return remote.fetchImages(request.query, request.pageSize, request.offset)
                .map { Response(0, it) } // Todo set offset property
    }

    class Request(val query: String, val pageSize: Int, val offset: Int)

    class Response(val nextPageOffset: Int, val images: List<Image>)
}
