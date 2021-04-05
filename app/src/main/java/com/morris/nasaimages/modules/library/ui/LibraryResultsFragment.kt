package com.morris.nasaimages.modules.library.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.databinding.FragmentLibraryResultsBinding
import com.morris.nasaimages.modules.favourites.data.local.FavouriteDataSource
import com.morris.nasaimages.modules.favourites.data.model.asFavourite
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModel
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModelFactory
import com.morris.nasaimages.modules.favourites.repository.FavouriteRepository
import com.morris.nasaimages.modules.library.LibraryRetrofitClient
import com.morris.nasaimages.modules.library.data.model.Item
import com.morris.nasaimages.modules.library.data.model.QueryLibrary
import com.morris.nasaimages.modules.library.data.remote.LibraryRemoteSource
import com.morris.nasaimages.modules.library.presentation.LibraryviewModel
import com.morris.nasaimages.modules.library.presentation.LibraryviewModelFactory
import com.morris.nasaimages.modules.library.repository.LibraryRepository
import com.morris.nasaimages.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LibraryResultsFragment :
    Fragment(R.layout.fragment_library_results),
    LibraryAdapter.OnLibraryClickListener,
    LibraryAdapter.OnNewPageListener{

    private lateinit var binding: FragmentLibraryResultsBinding
    private lateinit var libraryAdapter: LibraryAdapter

    private var page = 1

    private val viewModelLibrary by activityViewModels<LibraryviewModel> {
        LibraryviewModelFactory(
            LibraryRepository(
                LibraryRemoteSource(LibraryRetrofitClient.libraryWebService(LibraryRetrofitClient.retrofitInstance()))
            )
        )
    }

    private val viewModelFavs by activityViewModels<FavouritesViewModel> {
        FavouritesViewModelFactory(
            FavouriteRepository(
                FavouriteDataSource(AppDatabase.getRoomInstance(requireActivity().applicationContext))
            )
        )
    }

    private var queryLibrary: QueryLibrary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            queryLibrary = it.getParcelable("param1")
        }

        libraryAdapter = LibraryAdapter(requireContext(), this, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLibraryResultsBinding.bind(view)

        setRecyclerView()

        viewModelLibrary.loadLibrary(queryLibrary!!.queryText, page.toString(), queryLibrary!!.startYear, queryLibrary!!.endYear)

        setObservers()
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = libraryAdapter
    }

    private fun setObservers() {

        viewModelLibrary.isLoadig.observe(viewLifecycleOwner, {

            val visibility = if (it) View.VISIBLE else View.GONE

            binding.progressBar.visibility = visibility
        })

        viewModelLibrary.libraryData.observe(viewLifecycleOwner, {

            libraryAdapter.setList(it!!.collection.items)
        })

        viewModelLibrary.onMessageError.observe(viewLifecycleOwner, {

            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onLibraryClick(item: Item) {
        Toast.makeText(requireContext(), "Details ${item.data[0].title}", Toast.LENGTH_SHORT).show()
    }

    override fun onFavClick(item: Item, view: View) {

        // Necesito traer la imagen en hd

        viewModelFavs.saveFavourite(item.asFavourite())

        Utils.showSnackbar(view, "${item.data[0].title} added to favorites")
    }

    override fun onShareClick(item: Item) {
        Toast.makeText(requireContext(), "Share ${item.data[0].title}", Toast.LENGTH_SHORT).show()
    }

    override fun onSetWallpaperClick(item: Item, view: View) {
        Toast.makeText(requireContext(), "SET Wallpaper ${item.data[0].title}", Toast.LENGTH_SHORT).show()
    }

    override fun onNewPage() {

        if (viewModelLibrary.libraryData.value!!.collection.items.isNotEmpty()) {

            page += 1

            Toast.makeText(requireContext(), "loading more...",Toast.LENGTH_SHORT).show()

            viewModelLibrary.loadLibrary(queryLibrary!!.queryText, page.toString(), queryLibrary!!.startYear, queryLibrary!!.endYear)
        }
    }
}