package com.idealista.android.challenge.list.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.idealista.android.challenge.list.R
import com.idealista.android.challenge.list.ui.AdModel
import com.squareup.picasso.Picasso

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    interface AdListener {
        fun onAdClicked(ad: AdModel)

        fun onAdFavorite(ad: AdModel)
    }

    private var ads: List<AdModel> = emptyList()
    private lateinit var listener: AdListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_ad, parent, false)
        return ListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = ads.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(ads[position]) {
            if (thumbnail.isNotEmpty()) Picasso.with(holder.image.context).load(thumbnail).into(
                holder.image
            )
            holder.title.text = title
            holder.price.text = price
            holder.parent.setOnClickListener {
                listener.onAdClicked(this)
            }
            holder.favorite.isSelected = isFavorite
            holder.favorite.setOnClickListener {
                listener.onAdFavorite(this)
            }
        }
    }

    fun getItems() = ads

    fun set(listModel: ListModel) {
        val diffResult = DiffUtil.calculateDiff(AdsDiff(ads, listModel.ads))
        ads = listModel.ads
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateFavorites(favorites : Set<String>){
        val updatedAds = ads.map { it.copy(isFavorite = favorites.contains(it.id)) }
        val diffResult = DiffUtil.calculateDiff(AdsDiff(ads, updatedAds))
        ads = updatedAds
        diffResult.dispatchUpdatesTo(this)
    }

    fun listener(listener: AdListener) {
        this.listener = listener
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.ivAd)
        var favorite: ImageView = view.findViewById(R.id.tvFavorite)
        var title: TextView = view.findViewById(R.id.tvTitle)
        var price: TextView = view.findViewById(R.id.tvPrice)
        var parent: View = view.findViewById(R.id.parent)
    }

    inner class AdsDiff(private val oldList: List<AdModel>,
                        private val newList: List<AdModel>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}