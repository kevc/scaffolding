package me.kevcar.scaffolding.core.entity

data class ImagePage(
        val searchTerm: String,
        val requestedPageSize: Int,
        val images: List<Image>,
        val nextPage: Int)
