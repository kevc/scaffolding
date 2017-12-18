package me.kevcar.scaffolding.domain.interactor.image

import io.reactivex.Observable
import me.kevcar.scaffolding.core.entity.Image
import me.kevcar.scaffolding.core.entity.ImagePage
import me.kevcar.scaffolding.domain.datasource.RemoteImageDataSource
import javax.inject.Inject


class FetchImages @Inject constructor(private val remote: RemoteImageDataSource) {

    fun execute(request: Request): Observable<Response> {
        return remote.fetchImages(request.query, PAGE_SIZE, request.pageNumber)
                .map(::Response)
    }

    class Request(val query: String, val pageNumber: Int = 1)

    class Response(val imagePage: ImagePage)

    companion object {
        val PAGE_SIZE = 36
    }
}
