package me.kevcar.scaffolding.presentation.model

object Selectors {

    val PAGES_TO_IMAGES = { state: AppModel.State ->
        state.pages
                .flatMap {
                    it.images
                }
    }
}
