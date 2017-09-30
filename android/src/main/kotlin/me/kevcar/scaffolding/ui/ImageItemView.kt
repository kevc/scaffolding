package me.kevcar.scaffolding.ui

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyModel
import me.kevcar.scaffolding.R
import me.kevcar.scaffolding.core.entity.Image

class ImageItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, theme: Int = 0)
    : ImageView(context, attrs, theme) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val newHeightSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightSpec)
    }

    class Model(private val image: Image, private val clickListener: ImageClickListener) : EpoxyModel<ImageItemView>() {
        override fun getDefaultLayout() = R.layout.item_image

        override fun bind(view: ImageItemView) {

            view.setOnClickListener {
                clickListener.onImageClicked(image)
            }

            me.kevcar.scaffolding.app.Application.getComponent(view.context)
                    .picasso()
                    .load(image.thumbnailUrl)
                    .fit()
                    .placeholder(ColorDrawable(Color.parseColor("#000000")))
                    .into(view)
        }
    }

    interface ImageClickListener {
        fun onImageClicked(image: Image)
    }

}
