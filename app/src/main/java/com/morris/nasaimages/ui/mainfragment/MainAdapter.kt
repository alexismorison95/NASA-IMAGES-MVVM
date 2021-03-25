package com.morris.nasaimages.ui.mainfragment

import android.content.Context
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.morris.nasaimages.R
import com.morris.nasaimages.core.BaseViewHolder
import com.morris.nasaimages.data.model.main.MainItem
import com.morris.nasaimages.databinding.MainCardBinding
import com.squareup.picasso.Picasso

class MainAdapter(
    private val context: Context,
    private val list: List<MainItem>,
    private val itemClickListener: OnMainClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMainClickListener {

        fun onMainClick(item: MainItem, position: Int)
    }


    private lateinit var recyclerRoot: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerRoot = recyclerView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itemBinding = MainCardBinding.inflate(LayoutInflater.from(context), parent, false)

        return MainViewHolder(itemBinding, recyclerRoot)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {

        when (holder) {
            is MainViewHolder -> holder.bind(list[position], position)
        }
    }

    override fun getItemCount(): Int = list.size


    inner class MainViewHolder(
        private val binding: MainCardBinding,
        private val recyclerRoot: RecyclerView
    ) : BaseViewHolder<MainItem>(binding.root) {

        override fun bind(item: MainItem, position: Int) = with(binding) {

            // Get Image
            Picasso.get()
                .load(item.image)
                .into(image)

            title.text = item.title
            description.text = item.description
            textViewMoreInfo.text = item.moreInfo

            // Click More info
            btnMoreInfo.setOnClickListener {

                if (textViewMoreInfo.visibility == View.GONE) {

                    textViewMoreInfo.visibility = View.VISIBLE
                    btnMoreInfo.text = "Less info"

                    TransitionManager.beginDelayedTransition(recyclerRoot)
                }
                else {

                    textViewMoreInfo.visibility = View.GONE
                    btnMoreInfo.text = "More info"

                    TransitionManager.beginDelayedTransition(recyclerRoot)
                }
            }

            // Click in card
            binding.root.setOnClickListener {

                itemClickListener.onMainClick(item, position)
            }
        }
    }
}