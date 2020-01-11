package com.example.app_fyp.classes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import com.example.app_fyp.R
import kotlinx.android.synthetic.main.item_layout.view.*

class ComicAdapter(val items: ArrayList<Comic>, cont: Context) : RecyclerView.Adapter<ComicAdapter.ComicItemViewHolder>() {
    class ComicItemViewHolder(val relative : RelativeLayout) : RecyclerView.ViewHolder(relative){
        internal var title: TextView = relative.title
        // this does not get initalised
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object: ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int):Long{
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicAdapter.ComicItemViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false) as RelativeLayout
        // creates a new view and sets the views size, margins, padding, etc parameters
        return ComicItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ComicItemViewHolder, position: Int) {
        // assign/fill inthe values
        print(holder.title)
        holder.title!!.text  = items[position].name!!
    }

    override fun getItemCount() = items.size

    /*override fun getView(i :Int, view: View?, viewGroup: ViewGroup): View {
        var view = view

        val viewHolder: ComicItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(cont)
            view = inflater.inflate(R.layout.item_layout, viewGroup, false)

            viewHolder = ComicItemViewHolder()
            viewHolder.title = view!!.findViewById(R.id.title) as TextView
            viewHolder.image = view!!.findViewById(R.id.image) as ImageView
        } else {
            viewHolder = view.tag as ComicItemViewHolder
        }

        override fun onBindViewHolder(holder: My)
        val comic = getItem(i) // Comic? object

        viewHolder.title!!.text = comic!!.name

        viewHolder.image!!.setImageResource(R.drawable.c1)

        viewHolder.image!!.setOnClickListener {
            Toast.makeText(context, "Clicked image of " + comic!!.name,
                Toast.LENGTH_SHORT).show()
        }

        view.tag = viewHolder

        return view
    }*/
}


