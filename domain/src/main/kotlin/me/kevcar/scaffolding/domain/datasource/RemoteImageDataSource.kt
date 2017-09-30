package me.kevcar.scaffolding.domain.datasource

import io.reactivex.Single
import me.kevcar.scaffolding.core.entity.Image

interface RemoteImageDataSource {

    fun fetchImages(query: String, pageSize: Int, offset: Int): Single<List<Image>>

}
