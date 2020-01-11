package com.example.app_fyp.classes

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class MyLookup(private val recycleview : RecyclerView) : ItemDetailsLookup<Long>(){
    override fun getItemDetails(event: MotionEvent) : ItemDetails<Long>? {
        val view = recycleview.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recycleview.getChildViewHolder(view) as ComicAdapter.ComicItemViewHolder).getItemDetails()
        }
        return null
    }
}