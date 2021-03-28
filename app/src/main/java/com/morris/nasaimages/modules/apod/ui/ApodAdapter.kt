package com.morris.nasaimages.modules.apod.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morris.nasaimages.core.BaseViewHolder
import com.morris.nasaimages.modules.apod.data.model.Apod
import com.morris.nasaimages.databinding.ApodCardBinding
import com.squareup.picasso.Picasso

class ApodAdapter(
    private val context: Context,
    private val itemClickListener: OnApodClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnApodClickListener {

        fun onApodClick(item: Apod)
        fun onFavClick(item: Apod, view: View)
        fun onShareClick(item: Apod)
        fun onSetWallpaperClick(item: Apod, view: View)
    }


    private var list = listOf<Apod>()

    fun setList(listNew: List<Apod>) {

        this.list = listNew
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBinding = ApodCardBinding.inflate(LayoutInflater.from(context), parent, false)

        return ApodViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is ApodViewHolder -> holder.bind(list[position], position)
        }
    }

    override fun getItemCount(): Int = list.size


    inner class ApodViewHolder(private val binding: ApodCardBinding) : BaseViewHolder<Apod>(binding.root) {

        override fun bind(item: Apod, position: Int) = with(binding) {

            // Get Image
            Picasso.get()
                .load(item.url)
                .into(image)

            title.text = item.title
            date.text = item.date

            // Click in card
            binding.root.setOnClickListener {
                itemClickListener.onApodClick(item)
            }

            // Click on btn Add to Favs
            binding.addToFavs.setOnClickListener {
                itemClickListener.onFavClick(item, binding.addToFavs)
            }

            // Click on share btn
            binding.share.setOnClickListener {
                itemClickListener.onShareClick(item)
            }

            // Click on set wallpaper btn
            binding.btnSetWallpaper.setOnClickListener {
                itemClickListener.onSetWallpaperClick(item, binding.btnSetWallpaper)
            }
        }
    }
}