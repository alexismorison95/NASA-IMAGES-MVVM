package com.morris.nasaimages.ui.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morris.nasaimages.core.BaseViewHolder
import com.morris.nasaimages.data.model.favourites.Favourite
import com.morris.nasaimages.databinding.FavouriteCardBinding
import com.squareup.picasso.Picasso

class FavouritesAdapter(
    private val context: Context,
    private val itemClickListener: OnFavouriteClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnFavouriteClickListener {

        fun onSetWallpaperClick(item: Favourite)
        fun onDeleteClick(item: Favourite, position: Int, view: View)
    }


    private var list = listOf<Favourite>()

    fun setList(listNew: List<Favourite>) {

        this.list = listNew
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBinding = FavouriteCardBinding.inflate(LayoutInflater.from(context), parent, false)

        return FavViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is FavViewHolder -> holder.bind(this.list[position], position)
        }
    }

    override fun getItemCount(): Int = this.list.size


    inner class FavViewHolder(private val binding: FavouriteCardBinding) : BaseViewHolder<Favourite>(binding.root) {

        override fun bind(item: Favourite, position: Int) = with(binding) {

            // Get Image
            Picasso.get()
                .load(item.url)
                .into(image)

            title.text = item.title
            date.text = item.date

            // Click in set wallpaper
            binding.btnSetWallpaper.setOnClickListener {
                itemClickListener.onSetWallpaperClick(item)
            }

            // Click ein delete
            binding.btnDelete.setOnClickListener {
                itemClickListener.onDeleteClick(item, position, binding.btnDelete)
            }
        }
    }
}