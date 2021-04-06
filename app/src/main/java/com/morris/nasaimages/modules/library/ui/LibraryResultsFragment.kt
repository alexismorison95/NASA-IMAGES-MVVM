package com.morris.nasaimages.modules.library.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.application.MainActivity
import com.morris.nasaimages.core.Resource
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
import com.morris.nasaimages.modules.library.presentation.LibraryViewModel
import com.morris.nasaimages.modules.library.presentation.LibraryViewModelFactory
import com.morris.nasaimages.modules.library.repository.LibraryRepository
import com.morris.nasaimages.utils.Utils
import com.morris.nasaimages.utils.WallpaperService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class LibraryResultsFragment :
    Fragment(R.layout.fragment_library_results),
    LibraryAdapter.OnLibraryClickListener,
    LibraryAdapter.OnNewPageListener
{

    private lateinit var binding: FragmentLibraryResultsBinding
    private lateinit var libraryAdapter: LibraryAdapter

    private var page: Int? = null

    private val viewModelLibrary by viewModels<LibraryViewModel> {
        LibraryViewModelFactory(
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

        page = 1

        libraryAdapter = LibraryAdapter(requireContext(), this, this)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = queryLibrary!!.queryText.capitalize(Locale.getDefault())

        binding = FragmentLibraryResultsBinding.bind(view)

        setRecyclerView()

        viewModelLibrary.loadLibrary(
            queryLibrary!!.queryText,
            page.toString(),
            queryLibrary!!.startYear,
            queryLibrary!!.endYear
        )

        setObservers()
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = libraryAdapter

        //libraryAdapter.setList(listOf())
    }

    private fun setObservers() {

        viewModelLibrary.isLoadig.observe(viewLifecycleOwner, {

            val visibility = if (it) View.VISIBLE else View.GONE

            binding.progressBar.visibility = visibility
        })

        viewModelLibrary.libraryData.observe(viewLifecycleOwner, {

            libraryAdapter.updateList(it!!.collection.items)
        })

        viewModelLibrary.onMessageError.observe(viewLifecycleOwner, {

            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onLibraryClick(item: Item) {

        val bundle = Bundle()
        bundle.putParcelable("param1", item)

        findNavController().navigate(R.id.action_libraryResultsFragment_to_libraryDetailsFragment, bundle)
    }

    override fun onFavClick(item: Item, view: View) {

        if (item.data[0].hdUrl != null) {

            viewModelFavs.saveFavourite(item.asFavourite())
            Utils.showSnackbar(view, "${item.data[0].title} added to favorites")
        }
        else {

            viewModelLibrary.loadAsset(item.data[0].id).observe(viewLifecycleOwner, {

                item.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                viewModelFavs.saveFavourite(item.asFavourite())
                Utils.showSnackbar(view, "${item.data[0].title} added to favorites")
            })
        }
    }

    override fun onShareClick(item: Item) {

        if (item.data[0].hdUrl != null) {

            Utils.shareItem(requireContext(), item.data[0].hdUrl!!)
        }
        else {

            viewModelLibrary.loadAsset(item.data[0].id).observe(viewLifecycleOwner, {

                item.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                Utils.shareItem(requireContext(), item.data[0].hdUrl!!)
            })
        }
    }

    override fun onSetWallpaperClick(item: Item, view: View) {

        if (item.data[0].hdUrl != null) {

            WallpaperService.selectDialogWallpaper(requireContext(), view, item.data[0].hdUrl!!)
        }
        else {

            viewModelLibrary.loadAsset(item.data[0].id).observe(viewLifecycleOwner, {

                item.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                WallpaperService.selectDialogWallpaper(requireContext(), view, item.data[0].hdUrl!!)
            })
        }
    }

    override fun onNewPage() {

        if (viewModelLibrary.libraryData.value!!.collection.items.isNotEmpty()) {

            page = page?.plus(1)

            Utils.showSnackbar(binding.root, "loading more...")

            viewModelLibrary.loadLibrary(
                queryLibrary!!.queryText,
                page.toString(),
                queryLibrary!!.startYear,
                queryLibrary!!.endYear
            )
        }
    }
}