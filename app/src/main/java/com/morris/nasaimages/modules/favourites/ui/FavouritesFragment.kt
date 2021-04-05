package com.morris.nasaimages.modules.favourites.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.morris.nasaimages.R
import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.modules.favourites.data.local.FavouriteDataSource
import com.morris.nasaimages.modules.favourites.data.model.Favourite
import com.morris.nasaimages.databinding.FragmentFavouritesBinding
import com.morris.nasaimages.modules.favourites.repository.FavouriteRepository
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModel
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModelFactory
import com.morris.nasaimages.utils.DownloadService
import com.morris.nasaimages.utils.Utils
import com.morris.nasaimages.utils.WallpaperService
import kotlinx.coroutines.ExperimentalCoroutinesApi

class FavouritesFragment : Fragment(R.layout.fragment_favourites),
    FavouritesAdapter.OnFavouriteClickListener {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favouriteAdapter: FavouritesAdapter

    private var isCenterCropImage = true

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

    override fun onSetWallpaperClick(item: Favourite, view: View) {

        val url = item.hdurl ?: item.url

        WallpaperService.selectDialogWallpaper(requireContext(), view, url)
    }

    override fun onDeleteClick(item: Favourite, view: View) {

        val builder = MaterialAlertDialogBuilder(requireContext())

        with(builder) {

            this.setTitle("Remove ${item.title}?")
                .setMessage("This action can not be undone")
                .setNegativeButton("Cancel") { _, _ -> }
                .setPositiveButton("Accept") {_, _ ->

                    viewModel.deleteFavourite(item)

                    Utils.showSnackbar(view, "${item.title} removed from favorites ")
                }
            show()
        }
    }

    override fun onImageClick(view: ImageView) {

        isCenterCropImage = if (isCenterCropImage) {

            view.scaleType = ImageView.ScaleType.CENTER_INSIDE

            false
        }
        else {
            view.scaleType = ImageView.ScaleType.CENTER_CROP

            true
        }
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
                //searchView.setQuery("", false)
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