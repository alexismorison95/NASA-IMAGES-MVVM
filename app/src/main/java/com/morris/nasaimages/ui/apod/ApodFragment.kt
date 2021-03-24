package com.morris.nasaimages.ui.apod

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.application.MainActivity
import com.morris.nasaimages.application.RetrofitClient
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.local.AppDatabase
import com.morris.nasaimages.data.local.apod.ApodLocalSource
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.data.remote.apod.ApodRemoteSource
import com.morris.nasaimages.databinding.FragmentApodBinding
import com.morris.nasaimages.domain.apod.ApodRepository
import com.morris.nasaimages.presentation.apod.ApodViewModel
import com.morris.nasaimages.presentation.apod.ApodViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ApodFragment : Fragment(R.layout.fragment_apod), ApodAdapter.OnApodClickListener {

    private lateinit var binding: FragmentApodBinding

    private val viewModel by activityViewModels<ApodViewModel> {
        ApodViewModelFactory(
            ApodRepository(
                ApodRemoteSource(RetrofitClient.apodWebService(MainActivity.retrofitClient)),
                ApodLocalSource(AppDatabase.getRoomInstance(requireActivity().applicationContext))
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApodBinding.bind(view)

        setRecyclerView()

        setObservers()
    }

    private fun setObservers() {

        viewModel.isLoadig.observe(viewLifecycleOwner, {

            val visibility = if (it) View.VISIBLE else View.GONE

            binding.progressBar.visibility = visibility
        })

        viewModel.apodData.observe(viewLifecycleOwner, {

            binding.recyclerView.adapter = ApodAdapter(requireContext(), it, this)
        })

        viewModel.onMessageError.observe(viewLifecycleOwner, {

            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onFavClick(item: Apod) {

        Toast.makeText(requireContext(), "Click in fav", Toast.LENGTH_SHORT).show()
    }

    override fun onShareClick(item: Apod) {

        Toast.makeText(requireContext(), "Click in share", Toast.LENGTH_SHORT).show()
    }

    override fun onSetWallpaperClick(item: Apod) {

        Toast.makeText(requireContext(), "Click in set wallpaper", Toast.LENGTH_SHORT).show()
    }

    override fun onApodClick(item: Apod, position: Int) {

        val bundle = Bundle()
        bundle.putParcelable("param1", item)

        findNavController().navigate(R.id.action_apodFragment_to_apodDetailFragment, bundle)
    }
}