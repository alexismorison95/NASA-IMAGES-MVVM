package com.morris.nasaimages.modules.apod.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.morris.nasaimages.R
import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.modules.favourites.data.local.FavouriteDataSource
import com.morris.nasaimages.modules.apod.data.model.Apod
import com.morris.nasaimages.modules.favourites.data.model.asFavourite
import com.morris.nasaimages.databinding.FragmentApodDetailBinding
import com.morris.nasaimages.modules.favourites.repository.FavouriteRepository
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModel
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModelFactory
import com.morris.nasaimages.utils.DownloadService
import com.morris.nasaimages.utils.Utils
import com.morris.nasaimages.utils.WallpaperService
import com.squareup.picasso.Picasso

class ApodDetailFragment : Fragment(R.layout.fragment_apod_detail) {

    private lateinit var binding: FragmentApodDetailBinding
    private var isCenterCropImage = true

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

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApodDetailBinding.bind(view)

        setData()

        setListeners()
    }

    private fun setData() {

        Picasso.get()
            .load(apodItem!!.url)
            .into(binding.image)

        binding.title.text = apodItem!!.title
        binding.copyright.text = apodItem!!.copyright
        binding.date.text = apodItem!!.date
        binding.explanation.text = apodItem!!.explanation
    }

    private fun setListeners() {

        /*binding.addToFavs.setOnClickListener {

            viewModel.saveFavourite(apodItem!!.asFavourite())

            Utils.showSnackbar(binding.addToFavs, "${apodItem!!.title} added to favorites")
        }

        binding.share.setOnClickListener {

            Utils.shareItem(requireContext(), apodItem!!.hdurl)
        }*/

        binding.btnSetWallpaper.setOnClickListener {

            WallpaperService.selectDialogWallpaper(requireContext(), binding.btnSetWallpaper, apodItem!!.hdurl)
        }

        binding.btnDownloadWallpaper.setOnClickListener {

            Utils.showSnackbar(binding.btnDownloadWallpaper, "Downloading wallpaper, progress is shown in notifications")

            DownloadService.downloadImage(requireContext(), apodItem!!.hdurl, WallpaperService.NOT_SET_WALLPAPER)
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

                viewModel.saveFavourite(apodItem!!.asFavourite())

                Utils.showSnackbar(binding.root, "${apodItem!!.title} added to favorites")

                true
            }
            R.id.menuShare -> {

                Utils.shareItem(requireContext(), apodItem!!.hdurl)

                true
            }
            else -> { findNavController().navigateUp() }
        }
    }
}