package me.kevcar.scaffolding.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import me.kevcar.scaffolding.R

// TODO remove magic 4

class DividerDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val dimen = context.resources.getDimension(R.dimen.divider_width).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val viewPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        val isLastInRow = viewPosition > 0 && viewPosition % 4 == 3
        if (isLastInRow) {
            outRect.set(0, 0, 0, dimen)
        }
        else {
            outRect.set(0, 0, dimen, dimen)
        }
    }
}
