package com.morris.nasaimages.modules.library.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morris.nasaimages.core.BaseViewHolder
import com.morris.nasaimages.databinding.LibraryCardBinding
import com.morris.nasaimages.modules.apod.data.model.Apod
import com.morris.nasaimages.modules.library.data.model.Item
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class LibraryAdapter(
    private val context: Context,
    private val itemClickListener: OnLibraryClickListener,
    private val newPageListener: OnNewPageListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnLibraryClickListener {

        fun onLibraryClick(item: Item)
        fun onFavClick(item: Item, view: View)
        fun onShareClick(item: Item)
        fun onSetWallpaperClick(item: Item, view: View)
    }

    interface OnNewPageListener {
        fun onNewPage()
    }


    private var list = listOf<Item>()

    fun setList(listNew: List<Item>) {

        this.list = listNew
        notifyDataSetChanged()
    }

    fun updateList(listNew: List<Item>) {

        this.list = list + listNew
        notifyDataSetChanged()
    }

    fun getList(): List<Item> = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBinding = LibraryCardBinding.inflate(LayoutInflater.from(context), parent, false)

        return LibraryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is LibraryViewHolder -> {

                holder.bind(list[position], position)

                if (position == list.size - 1) {

                    newPageListener.onNewPage()
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size


    inner class LibraryViewHolder(private val binding: LibraryCardBinding) : BaseViewHolder<Item>(binding.root) {

        override fun bind(item: Item, position: Int) = with(binding) {

            // Get Image
            Picasso.get()
                .load(item.links[0].href)
                .into(image, object : Callback {

                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                })

            title.text = item.data[0].title
            date.text = item.data[0].dateCreated.split("T")[0]

            // Click in card
            binding.root.setOnClickListener {
                itemClickListener.onLibraryClick(item)
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