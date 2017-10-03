package me.kevcar.scaffolding.presentation.model

import redux.api.Reducer

object Reducer : Reducer<AppModel.State> {
    override fun reduce(state: AppModel.State, action: Any): AppModel.State {
        return when (action) {
            is AppModel.Action.ExecuteQuery -> {
                state.copy(pages = emptyList())
            }
            is AppModel.Action.AddPage -> {
                val oldLastPageOffset = state.pages.lastOrNull()?.nextPageOffset ?: 0
                val newPage = action.page
                val updatedNewPage = newPage.copy(
                        nextPageOffset = oldLastPageOffset
                                + newPage.requestedPageSize
                                + newPage.nextPageOffset
                )
                state.copy(pages = state.pages.plus(updatedNewPage))
            }
            else -> state
        }
    }
}
