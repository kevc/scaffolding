package me.kevcar.scaffolding.activity

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
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
import me.kevcar.scaffolding.ui.ImageItemView
import me.kevcar.scaffolding.ui.InfiniteScrollListener
import redux.asObservable

class MainActivity : AppCompatActivity(), ImageItemView.ImageClickListener {

    private val recyclerView: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recycler_view)
    }

    private val fullImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.full_size_image)
    }

    private val loadingView: View by lazy {
        findViewById<View>(R.id.loading_view)
    }

    private val imageGallery: View by lazy {
        findViewById<View>(R.id.image_bg)
    }

    private val appModel: AppModel by lazy {
        Application.getComponent(this).appModel()
    }

    private val picasso: Picasso by lazy {
        Application.getComponent(this).picasso()
    }

    private val layoutManager = GridLayoutManager(this, ROW_LENGTH)
    private val controller = ImageController(this, this)
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
                .subscribe(Consumer { controller.setImages(it) }, onError)
                .let { disposables.add(it) }

        stateChanges
                .connect()
                .let { disposables.add(it) }
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    private val onError = Consumer { error: Throwable ->
        Log.e("MainActivity", "Error", error)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, R.string.clipboard_permission_prompt, Toast.LENGTH_SHORT)
                            .show()
                }
                return
            }
        }
    }

    override fun onLongClick(image: Image) {
        val hasPermission = ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED


        if (hasPermission) {
            putOnClipboard(image)
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf("android.permission.WRITE_EXTERNAL_STORAGE"),
                    PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun putOnClipboard(image: Image) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val data = ClipData.newUri(contentResolver, "Image", Uri.parse(image.uri))
        clipboard.primaryClip = data
        Toast
                .makeText(this@MainActivity, R.string.clipboard_success, Toast.LENGTH_SHORT)
                .show()
    }

    override fun onImageClicked(image: Image) {
        loadingView.visibility = View.VISIBLE

        picasso.load(image.uri)
                .into(galleryTarget)

        imageGallery.visibility = View.VISIBLE
    }

    private val galleryTarget = object : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            fullImageView.setImageDrawable(null)
            loadingView.visibility = View.VISIBLE
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            // no op
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            loadingView.visibility = View.GONE
            bitmap?.let {
                fullImageView.setImageBitmap(it)
            }
        }

    }

    override fun onBackPressed() {
        if (imageGallery.visibility == View.VISIBLE) {
            imageGallery.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private val ROW_LENGTH = 4

        private val QUERY = "volcano"

        private val PERMISSION_REQUEST_CODE = 1234
    }
}
