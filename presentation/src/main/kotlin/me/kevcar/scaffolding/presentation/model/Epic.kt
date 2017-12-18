package me.kevcar.scaffolding.presentation.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import me.kevcar.scaffolding.domain.interactor.image.FetchImages
import redux.api.Store
import redux.observable.Epic
import javax.inject.Inject

class Epic @Inject constructor(private val fetchImages: FetchImages) : Epic<AppModel.State> {
    override fun map(actions: Observable<out Any>, store: Store<AppModel.State>): Observable<out Any> {
        return executeQuery(actions, store)
    }
    private fun executeQuery(actions: Observable<out Any>, store: Store<AppModel.State>): Observable<Any> {
        return actions.ofType(AppModel.Action.LoadNextPage::class.java)
                .flatMap { action ->
                    val nextPage = store.state.pages.lastOrNull()?.nextPage ?: 1
                    fetchImages.execute(FetchImages.Request(action.query, nextPage))
                            .map { AppModel.Action.AddPage(it.imagePage) }
                            .subscribeOn(Schedulers.io())
                }
    }
}
