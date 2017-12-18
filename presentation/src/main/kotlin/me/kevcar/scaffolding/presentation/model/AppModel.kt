package me.kevcar.scaffolding.presentation.model

import me.kevcar.scaffolding.core.entity.ImagePage
import redux.applyMiddleware
import redux.observable.createEpicMiddleware
import javax.inject.Inject

class AppModel @Inject constructor(epic: Epic) : redux.api.Store<AppModel.State> {

    data class State(
            val pages: List<ImagePage> = emptyList()
    )

    sealed class Action {
        class LoadNextPage(val query: String) : Action()
        class AddPage(val page: ImagePage) : Action()
    }

    // Redux Api Implementation

    private val store = redux.createStore(
            Reducer,
            State(),
            applyMiddleware(createEpicMiddleware(epic))
    )

    override fun replaceReducer(reducer: redux.api.Reducer<State>) {
        store.replaceReducer(reducer)
    }

    override fun subscribe(subscriber: redux.api.Store.Subscriber): redux.api.Store.Subscription {
        return store.subscribe(subscriber)
    }

    override fun getState(): me.kevcar.scaffolding.presentation.model.AppModel.State = store.state

    override fun dispatch(action: Any): Any = store.dispatch(action)
}
