package me.kevcar.scaffolding.presentation.entities

import me.kevcar.scaffolding.core.entity.Image

data class ImagePage(
        val searchTerm: String,
        val images: List<Image>,
        val nextPageOffset: Int)
