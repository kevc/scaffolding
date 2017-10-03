package me.kevcar.scaffolding.presentation.model

import redux.api.Reducer

object Reducer : Reducer<AppModel.State> {
    override fun reduce(state: AppModel.State, action: Any): AppModel.State {
        return when (action) {
            is AppModel.Action.ExecuteQuery -> {
                state.copy(pages = emptyList())
            }
            is AppModel.Action.AddPage -> {
                val updatedPages = state.pages.plus(action.page)
                state.copy(pages = updatedPages)
            }
            else -> state
        }
    }
}
