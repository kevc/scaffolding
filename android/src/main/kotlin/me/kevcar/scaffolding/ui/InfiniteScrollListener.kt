package me.kevcar.scaffolding.ui

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

class InfiniteScrollListener(
        private val pageListener: () -> Any,
        private val layoutManager: GridLayoutManager,
        private val visibleThreshold: Int) : RecyclerView.OnScrollListener() {

    private var loading = true
    private var evaluating = false

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!loading && !evaluating) {
            evaluating = true
            val itemCount = layoutManager.itemCount
            val lastItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
            if (itemCount <= lastItemPosition + visibleThreshold) {
                loading = true
                pageListener()
            }
        }
        evaluating = false
    }
}
