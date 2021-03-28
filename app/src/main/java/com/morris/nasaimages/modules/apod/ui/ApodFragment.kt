package com.morris.nasaimages.modules.apod.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.application.RetrofitClient
import com.morris.nasaimages.application.AppDatabase
import com.morris.nasaimages.modules.apod.data.local.ApodLocalSource
import com.morris.nasaimages.modules.favourites.data.local.FavouriteDataSource
import com.morris.nasaimages.modules.apod.data.model.Apod
import com.morris.nasaimages.modules.favourites.data.model.asFavourite
import com.morris.nasaimages.modules.apod.data.remote.ApodRemoteSource
import com.morris.nasaimages.databinding.FragmentApodBinding
import com.morris.nasaimages.modules.apod.repository.ApodRepository
import com.morris.nasaimages.modules.favourites.repository.FavouriteRepository
import com.morris.nasaimages.modules.apod.presentation.ApodViewModel
import com.morris.nasaimages.modules.apod.presentation.ApodViewModelFactory
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModel
import com.morris.nasaimages.modules.favourites.presentation.FavouritesViewModelFactory
import com.morris.nasaimages.utils.Utils
import com.morris.nasaimages.utils.WallpaperService
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ApodFragment : Fragment(R.layout.fragment_apod), ApodAdapter.OnApodClickListener {

    private lateinit var binding: FragmentApodBinding
    private lateinit var apodAdapter: ApodAdapter

    private val viewModelApod by activityViewModels<ApodViewModel> {
        ApodViewModelFactory(
            ApodRepository(
                ApodRemoteSource(RetrofitClient.apodWebService(RetrofitClient.retrofitApodInstance())),
                ApodLocalSource(AppDatabase.getRoomInstance(requireActivity().applicationContext))
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apodAdapter = ApodAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApodBinding.bind(view)

        setRecyclerView()

        setObservers()
    }

    private fun setObservers() {

        viewModelApod.isLoadig.observe(viewLifecycleOwner, {

            val visibility = if (it) View.VISIBLE else View.GONE

            binding.progressBar.visibility = visibility
        })

        viewModelApod.apodData.observe(viewLifecycleOwner, {
            
            apodAdapter.setList(it)
        })

        viewModelApod.onMessageError.observe(viewLifecycleOwner, {

            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = apodAdapter
    }

    override fun onFavClick(item: Apod, view: View) {

        viewModelFavs.saveFavourite(item.asFavourite())

        Utils.showSnackbar(view, "${item.title} added to favorites")
    }

    override fun onShareClick(item: Apod) {

        Utils.shareItem(requireContext(), item.hdurl)
    }

    override fun onSetWallpaperClick(item: Apod, view: View) {

        WallpaperService.selectDialogWallpaper(requireContext(), view, item.hdurl)
    }

    override fun onApodClick(item: Apod) {

        val bundle = Bundle()
        bundle.putParcelable("param1", item)

        findNavController().navigate(R.id.action_apodFragment_to_apodDetailFragment, bundle)
    }
}