package com.example.app_fyp.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_fyp.R
import com.example.app_fyp.information.activities.ComicActivity
import com.example.app_fyp.classes.Comic

class ComicAdapter(val items: ArrayList<Comic>, val cont: Activity) : RecyclerView.Adapter<ComicAdapter.ComicItemViewHolder>() {
    private lateinit var mContext : Context
    class ComicItemViewHolder(linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        internal var title: TextView = linearLayout.findViewById(R.id.tv1)
        internal var issues: TextView = linearLayout.findViewById(R.id.tv2)
        internal var card : CardView = linearLayout.findViewById(R.id.cardview)
        internal var ll : LinearLayout = linearLayout.findViewById(R.id.ll)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicItemViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.comic_card_layout, parent, false) as LinearLayout
        mContext = parent.context
        // creates a new view and sets the views size, margins, padding, etc parameters)
        return ComicItemViewHolder(
            inflater
        )
    }

    override fun onBindViewHolder(holder: ComicItemViewHolder, position: Int) {
        // assign/fill in the values
        holder.title.text  = items[position].name
        holder.issues.text = items[position].issue!!.joinToString(prefix = "Issues : ", separator = ", ")
        holder.ll.setBackgroundResource(R.drawable.c1)
        holder.ll.background.alpha= 120
        holder.card.setOnClickListener(){
                val intent = Intent(cont, ComicActivity::class.java)
                // find comic reference in lst list
                intent.putExtra("COMIC", items[position])
                cont.startActivityForResult(intent, 2)

        }
        //Glide.with(cont).load(items[position].image!!).into(holder.image!!)
    }
    override fun getItemCount() = items.size


}


