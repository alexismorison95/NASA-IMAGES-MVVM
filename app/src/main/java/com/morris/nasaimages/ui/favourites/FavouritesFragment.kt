package com.morris.nasaimages.ui.favourites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.morris.nasaimages.R
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.database.AppDatabase
import com.morris.nasaimages.data.local.database.FavouriteDataSource
import com.morris.nasaimages.databinding.FragmentFavouritesBinding
import com.morris.nasaimages.domain.database.FavouriteRepository
import com.morris.nasaimages.presentation.favourites.FavouritesViewModel
import com.morris.nasaimages.presentation.favourites.FavouritesViewModelFactory
import com.morris.nasaimages.ui.apod.ApodAdapter

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private lateinit var binding: FragmentFavouritesBinding

    private val viewModel by activityViewModels<FavouritesViewModel> {
        FavouritesViewModelFactory(
            FavouriteRepository(
                FavouriteDataSource(AppDatabase.getRoomInstance(requireActivity().applicationContext))
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFavouritesBinding.bind(view)

        viewModel.loadFavourites().observe(viewLifecycleOwner, { result ->

            when (result) {

                is Resource.Loading -> {

                }
                is Resource.Success -> {

                    Log.d("favs", "onViewCreated: ${result.data}")
                }
                is Resource.Failure -> {

                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}