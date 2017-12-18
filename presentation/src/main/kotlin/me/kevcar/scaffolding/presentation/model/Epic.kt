package me.kevcar.scaffolding.presentation.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import me.kevcar.scaffolding.domain.interactor.image.FetchImages
import me.kevcar.scaffolding.core.entity.ImagePage
import redux.api.Store
import redux.observable.Epic
import javax.inject.Inject

class Epic @Inject constructor(private val fetchImages: FetchImages) : Epic<AppModel.State> {
    override fun map(actions: Observable<out Any>, store: Store<AppModel.State>): Observable<out Any> {
        return Observable.merge(executeQuery(actions), loadNextPage(actions, store))
    }

    // TODO this could probably be combined into a single chunk of code,
    // where we switch between the initial query and the "next page" query
    // based upon whether we have any pages loaded into memory yet.

    private fun executeQuery(actions: Observable<out Any>): Observable<Any> {
        return actions.ofType(AppModel.Action.ExecuteQuery::class.java)
                .flatMap { action ->
                    fetchImages.execute(FetchImages.Request(action.query))
                            .map { AppModel.Action.AddPage(it.imagePage) }
                            .subscribeOn(Schedulers.io())
                }
    }

    private fun loadNextPage(actions: Observable<out Any>, store: Store<AppModel.State>): Observable<Any> {
        return actions.ofType(AppModel.Action.LoadNextPage::class.java)
                // Can't load next page if we don't have a page to derive offset from!
                .filter { store.state.pages.isNotEmpty() }
                .flatMap { action ->
                    val lastPage = store.state.pages.last()
                    val searchTerm = lastPage.searchTerm
                    val nextPage = lastPage.nextPage

                    fetchImages.execute(FetchImages.Request(searchTerm, nextPage))
                            .map { AppModel.Action.AddPage(it.imagePage) }
                            .subscribeOn(Schedulers.io())
                }
    }
}
