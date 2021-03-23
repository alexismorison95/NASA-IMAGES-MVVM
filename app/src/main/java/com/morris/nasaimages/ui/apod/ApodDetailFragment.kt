package com.morris.nasaimages.ui.apod

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.morris.nasaimages.R
import com.morris.nasaimages.application.MainActivity
import com.morris.nasaimages.application.RetrofitClient
import com.morris.nasaimages.data.local.database.AppDatabase
import com.morris.nasaimages.data.local.database.FavouriteDataSource
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.data.model.database.asFavourite
import com.morris.nasaimages.data.remote.apod.ApodDataSource
import com.morris.nasaimages.databinding.FragmentApodDetailBinding
import com.morris.nasaimages.domain.apod.ApodRepository
import com.morris.nasaimages.domain.database.FavouriteRepository
import com.morris.nasaimages.presentation.apod.ApodViewModel
import com.morris.nasaimages.presentation.apod.ApodViewModelFactory
import com.morris.nasaimages.presentation.favourites.FavouritesViewModel
import com.morris.nasaimages.presentation.favourites.FavouritesViewModelFactory
import com.squareup.picasso.Picasso

class ApodDetailFragment : Fragment(R.layout.fragment_apod_detail) {

    private lateinit var binding: FragmentApodDetailBinding

    private val viewModel by activityViewModels<FavouritesViewModel> {
        FavouritesViewModelFactory(
            FavouriteRepository(
                FavouriteDataSource(AppDatabase.getRoomInstance(requireActivity().applicationContext))
            )
        )
    }

    private var apodItem: Apod? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            apodItem = it.getParcelable("param1")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApodDetailBinding.bind(view)

        Picasso.get()
            .load(apodItem!!.url)
            .into(binding.image)

        binding.title.text = apodItem!!.title
        binding.copyright.text = apodItem!!.copyright
        binding.date.text = apodItem!!.date
        binding.explanation.text = apodItem!!.explanation

        binding.addToFavs.setOnClickListener {

            viewModel.saveFavourite(apodItem!!.asFavourite())

            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        }
    }

}