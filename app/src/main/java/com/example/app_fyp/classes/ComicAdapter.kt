package com.example.app_fyp.classes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.app_fyp.R

class ComicAdapter(items: ArrayList<Comic>, cont: Context) : ArrayAdapter<Comic>(cont, R.layout.item_layout, items) {
    private class ComicItemViewHolder {
        internal var image: ImageView? = null
        internal var title: TextView? = null
    }

    override fun getView(i :Int, view: View?, viewGroup: ViewGroup): View {
        var view = view

        val viewHolder: ComicItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_layout, viewGroup, false)

            viewHolder = ComicItemViewHolder()
            viewHolder.title = view!!.findViewById(R.id.title) as TextView
            viewHolder.image = view!!.findViewById(R.id.image) as ImageView
        } else {
            viewHolder = view.tag as ComicItemViewHolder
        }
        val comic = getItem(i) // Comic? object

        viewHolder.title!!.text = comic!!.name

        viewHolder.image!!.setImageResource(R.drawable.c1)

        viewHolder.image!!.setOnClickListener {
            Toast.makeText(context, "Clicked image of " + comic!!.name,
                Toast.LENGTH_SHORT).show()
        }

        view.tag = viewHolder

        return view
    }
}


