package me.kevcar.scaffolding.domain.interactor.image

import io.reactivex.Observable
import me.kevcar.scaffolding.core.entity.Image
import me.kevcar.scaffolding.domain.datasource.RemoteImageDataSource
import javax.inject.Inject


class FetchImages @Inject constructor(private val remote: RemoteImageDataSource) {

    fun execute(request: Request): Observable<Response> {
        return remote.fetchImages(request.query, request.pageSize, request.offset)
                .map { Response(0, it) }
    }

    class Request(val query: String, val pageSize: Int, val offset: Int)

    class Response(val nextPageOffset: Int, val images: List<Image>)
}
