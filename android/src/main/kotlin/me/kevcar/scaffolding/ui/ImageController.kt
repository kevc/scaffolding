package me.kevcar.scaffolding.ui

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import me.kevcar.scaffolding.R
import me.kevcar.scaffolding.core.entity.Image

class ImageController : EpoxyController() {

    private var images: List<Image> = emptyList()

    fun setImages(images: List<Image>) {
        this.images = images
        requestModelBuild()
    }

    override fun buildModels() {
        this.add(
                images.map { image -> ImageItemView.Model(image).id(java.util.Random().nextInt()) }
        )
    }

}
