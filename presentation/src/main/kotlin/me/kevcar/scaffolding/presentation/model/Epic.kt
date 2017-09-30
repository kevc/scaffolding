package me.kevcar.scaffolding.presentation.model

import io.reactivex.Observable
import me.kevcar.scaffolding.domain.interactor.image.FetchImages
import redux.api.Store
import redux.observable.Epic
import javax.inject.Inject

class Epic @Inject constructor(private val fetchImages: FetchImages) : Epic<AppModel.State> {
    override fun map(actions: Observable<out Any>, store: Store<AppModel.State>): Observable<out Any> {
        return Observable.just(Unit)
    }
}
