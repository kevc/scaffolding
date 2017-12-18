package me.kevcar.scaffolding.data.api.response

import me.kevcar.scaffolding.data.api.entity.Image


class ImageResponse(
        val resultCount: Int,
        val images: List<Image>)
