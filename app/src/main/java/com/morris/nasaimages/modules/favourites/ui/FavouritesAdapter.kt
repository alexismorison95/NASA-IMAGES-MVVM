package com.morris.nasaimages.modules.favourites.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.morris.nasaimages.core.BaseViewHolder
import com.morris.nasaimages.modules.favourites.data.model.Favourite
import com.morris.nasaimages.databinding.FavouriteCardBinding
import com.morris.nasaimages.databinding.FavouriteCardMinBinding
import com.squareup.picasso.Picasso

class FavouritesAdapter(
    private val context: Context,
    private val itemClickListener: OnFavouriteClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnFavouriteClickListener {

        fun onCardClick(item: Favourite, view: View)
    }


    private var list = listOf<Favourite>()

    fun setList(listNew: List<Favourite>) {

        this.list = listNew
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBindingMin = FavouriteCardMinBinding.inflate(LayoutInflater.from(context), parent, false)

        return FavViewHolderMin(itemBindingMin)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is FavViewHolderMin -> holder.bind(this.list[position], position)
        }
    }

    override fun getItemCount(): Int = this.list.size


    inner class FavViewHolderMin(private val binding: FavouriteCardMinBinding) : BaseViewHolder<Favourite>(binding.root) {

        override fun bind(item: Favourite, position: Int) = with(binding) {

            Picasso.get()
                .load(item.url)
                .into(image)

            binding.root.setOnClickListener {
                itemClickListener.onCardClick(item, binding.image)
            }
        }
    }
}