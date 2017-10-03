package me.kevcar.scaffolding.core.entity

import me.kevcar.scaffolding.core.entity.Image

data class ImagePage(
        val searchTerm: String,
        val requestedPageSize: Int,
        val images: List<Image>,
        val nextPageOffset: Int)
