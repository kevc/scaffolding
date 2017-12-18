package me.kevcar.scaffolding.data.api.entity

import com.google.gson.annotations.SerializedName
import me.kevcar.scaffolding.core.entity.Image as CoreImage

data class Image(
        val id: String,
        val title: String,
        @SerializedName("display_sizes") val meta: List<Meta>) {

    data class Meta(
            val isWaterMarked: Boolean,
            val name: String,
            val uri: String)

    fun toCore(): CoreImage {
        val detail = meta.first()
        return CoreImage(id, title, detail.uri, detail.isWaterMarked)
    }
}
