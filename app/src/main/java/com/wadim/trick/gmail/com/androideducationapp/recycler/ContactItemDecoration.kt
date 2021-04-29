package com.wadim.trick.gmail.com.androideducationapp.recycler

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class ContactItemDecoration(private val offsetInDp: Int, private val borderDrawable: Drawable): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let {
            outRect.bottom = when(parent.getChildAdapterPosition(view)) {
                 RecyclerView.NO_POSITION, state.itemCount - 1 -> 0
                 else -> offsetInDp
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.adapter == null)
            return
        parent.children.forEach {view ->
            when(parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION, state.itemCount - 1 -> return
                else -> view.background = borderDrawable
            }

        }
    }
}