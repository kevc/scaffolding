package me.kevcar.scaffolding.presentation.model

import me.kevcar.scaffolding.core.entity.Image

object Selectors {

    val PAGES_TO_IMAGES = { state: AppModel.State ->
        emptyList<Image>()
    }
}
