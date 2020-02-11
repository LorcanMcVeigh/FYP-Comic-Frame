package com.example.app_fyp.classes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_fyp.R
import com.bumptech.glide.Glide
import com.example.app_fyp.AddComicActivity
import com.example.app_fyp.ComicActivity

class FaveComicAdapter(val items: ArrayList<Comic>, val cont: Activity) : RecyclerView.Adapter<FaveComicAdapter.FaveComicItemViewHolder>() {
    private lateinit var mContext : Context
    class FaveComicItemViewHolder(cv: CardView) : RecyclerView.ViewHolder(cv){
        internal var title: TextView = cv.findViewById(R.id.tv1)
        internal var card : CardView = cv.findViewById(R.id.favecardview)
        //internal var ll : LinearLayout = linearLayout.findViewById(R.id.linlayout)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaveComicItemViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.faves_comic_card_layout, parent, false) as CardView
        mContext = parent.context
        // creates a new view and sets the views size, margins, padding, etc parameters)
        return FaveComicItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: FaveComicItemViewHolder, position: Int) {
        // assign/fill in the values
        holder.title.text  = items[position].name
        //holder.ll.setBackgroundResource(R.drawable.c1)
        //holder.ll.background.alpha= 120
        holder.card.setBackgroundResource(R.drawable.c1)
        holder.card.background.alpha = 120
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


