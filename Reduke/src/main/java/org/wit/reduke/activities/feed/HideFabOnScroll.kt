package org.wit.reduke.activities.feed

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView

class HideFabOnScroll(val recyclerView: RecyclerView, val floatingActionButton: FloatingActionButton) {

    fun start() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    if (floatingActionButton!!.isShown) {
                        floatingActionButton?.hide()
                    }
                } else if (dy < 0) {
                    if (!floatingActionButton!!.isShown) {
                        floatingActionButton?.show()
                    }
                }
            }
        })
    }
}