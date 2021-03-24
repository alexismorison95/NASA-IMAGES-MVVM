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
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.database.AppDatabase
import com.morris.nasaimages.data.local.database.FavouriteDataSource
import com.morris.nasaimages.data.model.database.Favourite
import com.morris.nasaimages.databinding.FragmentFavouritesBinding
import com.morris.nasaimages.domain.database.FavouriteRepository
import com.morris.nasaimages.presentation.favourites.FavouritesViewModel
import com.morris.nasaimages.presentation.favourites.FavouritesViewModelFactory
import com.morris.nasaimages.ui.apod.ApodAdapter

class FavouritesFragment : Fragment(R.layout.fragment_favourites), FavouritesAdapter.OnFavouriteClickListener {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favouriteAdapter: FavouritesAdapter

    private val viewModel by activityViewModels<FavouritesViewModel> {
        FavouritesViewModelFactory(
            FavouriteRepository(
                FavouriteDataSource(AppDatabase.getRoomInstance(requireActivity().applicationContext))
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favouriteAdapter = FavouritesAdapter(requireContext(), this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFavouritesBinding.bind(view)

        setRecyclerView()

        setObservers()
    }

    private fun setObservers() {

        viewModel.loadFavourites().observe(viewLifecycleOwner, { result ->

            when (result) {

                is Resource.Loading -> {

                    binding.recyclerView.adapter = null
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {

                    binding.progressBar.visibility = View.GONE
                    favouriteAdapter.setList(result.data)

                    binding.recyclerView.adapter = favouriteAdapter
                }
                is Resource.Failure -> {

                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onSetWallpaperClick(item: Favourite) {

        Toast.makeText(requireContext(), "Click in set wallpaper", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(item: Favourite, position: Int) {

        viewModel.deleteFavourite(item)

        val list = favouriteAdapter.getList().toMutableList().apply { removeAt(position) }
        favouriteAdapter.setList(list)
    }
}