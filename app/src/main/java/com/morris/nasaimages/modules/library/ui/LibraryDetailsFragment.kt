package com.morris.nasaimages.modules.library.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.morris.nasaimages.R
import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.application.MainActivity
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.databinding.FragmentApodDetailBinding
import com.morris.nasaimages.databinding.FragmentLibraryDetailsBinding
import com.morris.nasaimages.modules.favourites.data.local.FavouriteDataSource
import com.morris.nasaimages.modules.favourites.data.model.asFavourite
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModel
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModelFactory
import com.morris.nasaimages.modules.favourites.repository.FavouriteRepository
import com.morris.nasaimages.modules.library.LibraryRetrofitClient
import com.morris.nasaimages.modules.library.data.model.Item
import com.morris.nasaimages.modules.library.data.remote.LibraryRemoteSource
import com.morris.nasaimages.modules.library.presentation.LibraryViewModel
import com.morris.nasaimages.modules.library.presentation.LibraryViewModelFactory
import com.morris.nasaimages.modules.library.repository.LibraryRepository
import com.morris.nasaimages.utils.DownloadService
import com.morris.nasaimages.utils.Utils
import com.morris.nasaimages.utils.WallpaperService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class LibraryDetailsFragment : Fragment(R.layout.fragment_library_details) {

    private lateinit var binding: FragmentLibraryDetailsBinding
    private var isCenterCropImage = true

    private val viewModelFavs by activityViewModels<FavouritesViewModel> {
        FavouritesViewModelFactory(
            FavouriteRepository(
                FavouriteDataSource(AppDatabase.getRoomInstance(requireActivity().applicationContext))
            )
        )
    }

    val repository = LibraryRepository(
        LibraryRemoteSource(LibraryRetrofitClient.libraryWebService(LibraryRetrofitClient.retrofitInstance()))
    )

    private val viewModelLibrary by viewModels<LibraryViewModel> {
        LibraryViewModelFactory(
            repository
        )
    }

    private var libraryItem: Item? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            libraryItem = it.getParcelable("param1")
        }

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = libraryItem!!.data[0].title

        binding = FragmentLibraryDetailsBinding.bind(view)

        setData()

        setListeners()
    }

    private fun setData() {

        Picasso.get()
            .load(libraryItem!!.links[0].href)
            .into(binding.image)

        binding.title.text = libraryItem!!.data[0].title
        binding.copyright.text = libraryItem!!.data[0].center
        binding.date.text = libraryItem!!.data[0].dateCreated.split("T")[0]
        binding.explanation.text = libraryItem!!.data[0].description
    }

    private fun setListeners() {

        /*binding.addToFavs.setOnClickListener {

            if (libraryItem!!.data[0].hdUrl != null) {

                viewModelFavs.saveFavourite(libraryItem!!.asFavourite())
                Utils.showSnackbar(binding.addToFavs, "${libraryItem!!.data[0].title} added to favorites")
            }
            else {

                viewModelLibrary.loadAsset(libraryItem!!.data[0].id).observe(viewLifecycleOwner, {

                    libraryItem!!.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                    viewModelFavs.saveFavourite(libraryItem!!.asFavourite())
                    Utils.showSnackbar(binding.addToFavs, "${libraryItem!!.data[0].title} added to favorites")
                })
            }
        }

        binding.share.setOnClickListener {

            if (libraryItem!!.data[0].hdUrl != null) {

                Utils.shareItem(requireContext(), libraryItem!!.data[0].hdUrl!!)
            }
            else {

                viewModelLibrary.loadAsset(libraryItem!!.data[0].id).observe(viewLifecycleOwner, {

                    libraryItem!!.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                    Utils.shareItem(requireContext(), libraryItem!!.data[0].hdUrl!!)
                })
            }
        }*/

        binding.btnSetWallpaper.setOnClickListener {

            if (libraryItem!!.data[0].hdUrl != null) {

                WallpaperService.selectDialogWallpaper(requireContext(), binding.btnSetWallpaper, libraryItem!!.data[0].hdUrl!!)
            }
            else {

                viewModelLibrary.loadAsset(libraryItem!!.data[0].id).observe(viewLifecycleOwner, {

                    libraryItem!!.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                    WallpaperService.selectDialogWallpaper(requireContext(), binding.btnSetWallpaper, libraryItem!!.data[0].hdUrl!!)
                })
            }
        }

        binding.btnDownloadWallpaper.setOnClickListener {

            if (libraryItem!!.data[0].hdUrl != null) {

                Utils.showSnackbar(binding.btnDownloadWallpaper, "Downloading wallpaper, progress is shown in notifications")
                DownloadService.downloadImage(requireContext(), libraryItem!!.data[0].hdUrl!!, WallpaperService.NOT_SET_WALLPAPER)
            }
            else {

                viewModelLibrary.loadAsset(libraryItem!!.data[0].id).observe(viewLifecycleOwner, {

                    libraryItem!!.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                    Utils.showSnackbar(binding.btnDownloadWallpaper, "Downloading wallpaper, progress is shown in notifications")
                    DownloadService.downloadImage(requireContext(), libraryItem!!.data[0].hdUrl!!, WallpaperService.NOT_SET_WALLPAPER)
                })
            }
        }

        binding.image.setOnClickListener {

            isCenterCropImage = if (isCenterCropImage) {

                binding.image.scaleType = ImageView.ScaleType.CENTER_INSIDE

                false
            }
            else {
                binding.image.scaleType = ImageView.ScaleType.CENTER_CROP

                true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.menuAddToFavs -> {

                if (libraryItem!!.data[0].hdUrl != null) {

                    viewModelFavs.saveFavourite(libraryItem!!.asFavourite())
                    Utils.showSnackbar(binding.root, "${libraryItem!!.data[0].title} added to favorites")
                }
                else {

                    viewModelLibrary.loadAsset(libraryItem!!.data[0].id).observe(viewLifecycleOwner, {

                        libraryItem!!.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                        viewModelFavs.saveFavourite(libraryItem!!.asFavourite())
                        Utils.showSnackbar(binding.root, "${libraryItem!!.data[0].title} added to favorites")
                    })
                }

                true
            }
            R.id.menuShare -> {

                if (libraryItem!!.data[0].hdUrl != null) {

                    Utils.shareItem(requireContext(), libraryItem!!.data[0].hdUrl!!)
                }
                else {

                    viewModelLibrary.loadAsset(libraryItem!!.data[0].id).observe(viewLifecycleOwner, {

                        libraryItem!!.data[0].hdUrl = it.collection.items[0].href.replace("http", "https")

                        Utils.shareItem(requireContext(), libraryItem!!.data[0].hdUrl!!)
                    })
                }

                true
            }
            else -> { findNavController().navigateUp() }
        }
    }
}