package me.kevcar.scaffolding.ui

import android.view.View
import com.airbnb.epoxy.EpoxyController
import me.kevcar.scaffolding.core.entity.Image

class ImageController(private val clickListener: ImageItemView.ImageClickListener) : EpoxyController() {

    private var images: List<Image> = emptyList()

    fun setImages(images: List<Image>) {
        this.images = images
        requestModelBuild()
    }

    override fun buildModels() {
        images.forEachIndexed { index, image ->
            ImageItemView
                    .Model(image, clickListener)
                    .id(index)
                    .let { add(it) }
        }
    }

}
