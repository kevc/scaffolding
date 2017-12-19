package me.kevcar.scaffolding.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import me.kevcar.scaffolding.R
import me.kevcar.scaffolding.app.Application
import me.kevcar.scaffolding.core.entity.Image
import me.kevcar.scaffolding.presentation.model.AppModel
import me.kevcar.scaffolding.presentation.model.Selectors
import me.kevcar.scaffolding.ui.DividerDecoration
import me.kevcar.scaffolding.ui.ImageController
import me.kevcar.scaffolding.ui.InfiniteScrollListener
import redux.asObservable

class MainActivity : AppCompatActivity() {

    private val appModel: AppModel by lazy {
        Application.getComponent(this).appModel()
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recycler_view)
    }

    private val layoutManager = GridLayoutManager(this, ROW_LENGTH)
    private val controller = ImageController(this)
    private val disposables = CompositeDisposable()

    private val loadNextPage = {
        appModel.dispatch(AppModel.Action.LoadNextPage(QUERY))
        Unit
    }
    private val listener = InfiniteScrollListener(loadNextPage, layoutManager, 16)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = controller.adapter
        recyclerView.addOnScrollListener(listener)
        recyclerView.addItemDecoration(DividerDecoration(this))

        appModel.dispatch(AppModel.Action.LoadNextPage(QUERY))

        val stateChanges = appModel.asObservable()
                .map(Selectors.PAGES_TO_IMAGES)
                .distinctUntilChanged()
                .publish()

        stateChanges
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { listener.setLoading(false) }
                .subscribe(imagesConsumer, onError)
                .let { disposables.add(it) }

        stateChanges
                .connect()
                .let { disposables.add(it) }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    private val imagesConsumer = Consumer { images: List<Image> ->
        controller.setImages(images)
    }

    private val onError = Consumer { error: Throwable ->
        Log.e("MainActivity", "Error", error)
    }

    companion object {
        private val ROW_LENGTH = 4

        private val QUERY = "volcano"
    }
}
