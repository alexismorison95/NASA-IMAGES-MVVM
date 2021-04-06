package com.morris.nasaimages.modules.favourites.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.morris.nasaimages.R
import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.databinding.FragmentFavouriteDetailBinding
import com.morris.nasaimages.modules.favourites.data.local.FavouriteDataSource
import com.morris.nasaimages.modules.favourites.data.model.Favourite
import com.morris.nasaimages.modules.favourites.data.model.asFavourite
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModel
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModelFactory
import com.morris.nasaimages.modules.favourites.repository.FavouriteRepository
import com.morris.nasaimages.utils.DownloadService
import com.morris.nasaimages.utils.Utils
import com.morris.nasaimages.utils.WallpaperService
import com.squareup.picasso.Picasso

class FavouriteDetailFragment : Fragment(R.layout.fragment_favourite_detail) {

    private lateinit var binding: FragmentFavouriteDetailBinding
    private var isCenterCropImage = true

    private val viewModelFavs by activityViewModels<FavouritesViewModel> {
        FavouritesViewModelFactory(
            FavouriteRepository(
                FavouriteDataSource(AppDatabase.getRoomInstance(requireActivity().applicationContext))
            )
        )
    }

    private var favItem: Favourite? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            favItem = it.getParcelable("param1")
        }

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFavouriteDetailBinding.bind(view)

        setData()

        setListeners()
    }

    private fun setData() {

        Picasso.get()
            .load(favItem!!.url)
            .into(binding.image)

        binding.title.text = favItem!!.title
        binding.date.text = favItem!!.date.split(" ")[0]
        binding.copyright.text = favItem!!.copyright
        binding.explanation.text = favItem!!.explanation
    }

    private fun setListeners() {

        binding.btnSetWallpaper.setOnClickListener {

            WallpaperService.selectDialogWallpaper(requireContext(), binding.btnSetWallpaper, favItem!!.hdurl!!)
        }

        binding.btnDownloadWallpaper.setOnClickListener {

            Utils.showSnackbar(binding.btnDownloadWallpaper, "Downloading wallpaper, progress is shown in notifications")

            DownloadService.downloadImage(requireContext(), favItem!!.hdurl!!, WallpaperService.NOT_SET_WALLPAPER)
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

        inflater.inflate(R.menu.fav_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.menuDelete -> {

                val builder = MaterialAlertDialogBuilder(requireContext())

                with(builder) {

                    this.setTitle("Remove ${favItem!!.title}?")
                        .setMessage("This action can not be undone")
                        .setNegativeButton("Cancel") { _, _ -> }
                        .setPositiveButton("Accept") {_, _ ->

                            viewModelFavs.deleteFavourite(favItem!!)

                            Utils.showSnackbar(binding.root, "${favItem!!.title} removed from favorites ")

                            findNavController().navigateUp()
                        }
                    show()
                }

                true
            }
            R.id.menuShare -> {

                Utils.shareItem(requireContext(), favItem!!.hdurl!!)

                true
            }
            else -> { findNavController().navigateUp() }
        }
    }
}