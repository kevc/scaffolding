package me.kevcar.scaffolding.presentation.model

import redux.api.Reducer

object Reducer : Reducer<AppModel.State> {
    override fun reduce(state: AppModel.State, action: Any): AppModel.State {
        return when (action) {
            is AppModel.Action.SetImages -> {
                state.copy(images = action.images)
            }
            else -> state
        }
    }
}
