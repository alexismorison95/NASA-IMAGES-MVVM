package com.morris.nasaimages.ui.favourites

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.application.MainActivity
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.AppDatabase
import com.morris.nasaimages.data.local.favourites.FavouriteDataSource
import com.morris.nasaimages.data.model.favourites.Favourite
import com.morris.nasaimages.databinding.FragmentFavouritesBinding
import com.morris.nasaimages.domain.favourites.FavouriteRepository
import com.morris.nasaimages.presentation.favourites.FavouritesViewModel
import com.morris.nasaimages.presentation.favourites.FavouritesViewModelFactory
import com.morris.nasaimages.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
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

        setHasOptionsMenu(true)

        viewModel.loadFavourites()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFavouritesBinding.bind(view)

        setRecyclerView()

        setObservers()
    }

    private fun setObservers() {

        viewModel.isLoadig.observe(viewLifecycleOwner, {

            val visibility = if (it) View.VISIBLE else View.GONE

            binding.progressBar.visibility = visibility
        })

        viewModel.favouritesData.observe(viewLifecycleOwner, {

            favouriteAdapter.setList(it!!)
        })

        viewModel.onMessageError.observe(viewLifecycleOwner, {

            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
        })

        viewModel.fetchFavourites.observe(viewLifecycleOwner, {

            favouriteAdapter.setList(it)
        })
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = favouriteAdapter
    }

    override fun onSetWallpaperClick(item: Favourite) {

        Toast.makeText(requireContext(), "Click in set wallpaper", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(item: Favourite, position: Int, view: View) {

        viewModel.deleteFavourite(item)

        Utils.showSnackbar(view, "${item.title} removed from favorites ")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.fav_menu, menu)

        setSearchView(menu)
    }

    private fun setSearchView(menu: Menu) {

        val manager: SearchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val item = menu.findItem(R.id.searchMenu)
        val searchView = item.actionView as SearchView

        searchView.queryHint = "Search by title"

        searchView.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                searchView.clearFocus()
                searchView.setQuery("", false)
                item.collapseActionView()

                viewModel.setQuerySearch(query!!)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.setQuerySearch(newText!!)

                return true
            }
        })

        searchView.setOnCloseListener {

            item.collapseActionView()

            true
        }

        item.setOnMenuItemClickListener {

            searchView.onActionViewExpanded()

            true
        }
    }
}