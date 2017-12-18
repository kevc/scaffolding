package me.kevcar.scaffolding.presentation.model

import redux.api.Reducer

object Reducer : Reducer<AppModel.State> {
    override fun reduce(state: AppModel.State, action: Any): AppModel.State {
        return when (action) {
            is AppModel.Action.AddPage -> {
                val newPage = action.page
                val updatedNewPage = newPage.copy(
                        nextPage = newPage.nextPage + 1
                )
                state.copy(pages = state.pages.plus(updatedNewPage))
            }
            else -> state
        }
    }
}
