package me.kevcar.scaffolding.activity

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import me.kevcar.scaffolding.R
import me.kevcar.scaffolding.app.Application
import me.kevcar.scaffolding.core.entity.Image
import me.kevcar.scaffolding.presentation.model.AppModel
import me.kevcar.scaffolding.ui.ImageController
import me.kevcar.scaffolding.ui.ImageItemView
import redux.asObservable

class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recycler_view)
    }

    private val appModel: AppModel by lazy {
        Application.getComponent(this).appModel()
    }

    private val layoutManager = GridLayoutManager(this, 4)
    private val clickListener = object : ImageItemView.ImageClickListener {

        override fun onImageClicked(image: Image) {
            // TODO show full screen image here.
            Log.d("Tag", "Showing image here.")
        }
    }

    private val controller = ImageController(clickListener)
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = controller.adapter

        appModel.dispatch(AppModel.Action.ExecuteQuery("animal"))

        val stateChanges = appModel.asObservable()
                .map { it.images }
                .publish()

        stateChanges
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ controller.setImages(it) })
                .let { disposables.add(it) }

        stateChanges
                .connect()
                .let { disposables.add(it) }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
