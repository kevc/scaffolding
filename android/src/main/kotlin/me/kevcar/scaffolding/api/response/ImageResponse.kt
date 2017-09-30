package me.kevcar.scaffolding.api.response

import com.google.gson.annotations.SerializedName
import me.kevcar.scaffolding.core.entity.Image

class ImageResponse(
        @SerializedName("nextOffsetAddCount") val nextPageOffset: Int,
        @SerializedName("value") val images: List<Image>)
