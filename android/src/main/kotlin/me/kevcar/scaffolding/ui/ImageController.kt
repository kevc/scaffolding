package me.kevcar.scaffolding.ui

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import me.kevcar.scaffolding.core.entity.Image

class ImageController(
        private val context: Context) : EpoxyController() {

    private var images: List<Image> = emptyList()

    fun setImages(images: List<Image>) {
        this.images = images
        requestModelBuild()
    }

    override fun buildModels() {
        images.forEachIndexed { index, image ->
            ImageItemView
                    .Model(context, image)
                    .id(index)
                    .let { add(it) }
        }
    }

}
