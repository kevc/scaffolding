package me.kevcar.scaffolding.domain.interactor.image

import io.reactivex.Observable
import me.kevcar.scaffolding.core.entity.ImagePage
import me.kevcar.scaffolding.domain.datasource.RemoteImageDataSource
import javax.inject.Inject


class FetchImages @Inject constructor(private val remote: RemoteImageDataSource) {

    fun execute(request: Request): Observable<Response> {
        throw NotImplementedError()
    }

    class Request(val query: String, val pageNumber: Int = 1)

    class Response(val imagePage: ImagePage)
}
