package com.example.app_fyp.ui.home

import android.app.Activity
import android.app.ActivityOptions
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_fyp.R
import com.example.app_fyp.information.activities.ComicActivity
import com.example.app_fyp.classes.Comic
import java.security.AccessController.getContext
import android.util.Pair as UtilPair

class ComicAdapter(val items: ArrayList<Comic>, val cont: Activity, val groupname  : ArrayList<String>) : RecyclerView.Adapter<ComicAdapter.ComicItemViewHolder>() {
    private lateinit var mContext : Context
    class ComicItemViewHolder(linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        internal var title: TextView = linearLayout.findViewById(R.id.tv1)
        internal var issues: TextView = linearLayout.findViewById(R.id.tv2)
        internal var card : CardView = linearLayout.findViewById(R.id.cardview)
        internal var ll : RelativeLayout = linearLayout.findViewById(R.id.ll)
        internal var i : ImageView = linearLayout.findViewById(R.id.i)
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
        //holder.issues.text = items[position].issue!!.joinToString(prefix = "Issues : ", separator = ", ")
        holder.i.setBackgroundResource(R.drawable.c1)
        holder.i.background.alpha= 120
        holder.card.setOnClickListener(){
            val intent = Intent(cont, ComicActivity::class.java)
            // find comic reference in lst list
            intent.putExtra("COMIC", items[position])
            intent.putExtra("GROUPNAMES", groupname)
            holder.i.transitionName = holder.title.toString()
            holder.title.transitionName = "text"
            val p1 = UtilPair<View, String>(holder.i, "image")
            val p2 = UtilPair<View, String>(holder.title, "text")
            val options = ActivityOptions.makeSceneTransitionAnimation(cont, p1, p2)
            cont.startActivityForResult(intent,  2, options.toBundle())

        }
        //Glide.with(cont).load(items[position].image!!).into(holder.image!!)
    }
    override fun getItemCount() = items.size


}


