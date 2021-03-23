package com.morris.nasaimages.ui.apod

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.application.AppConstants.API_KEY
import com.morris.nasaimages.application.MainActivity
import com.morris.nasaimages.application.RetrofitClient
import com.morris.nasaimages.core.Resource
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.data.remote.apod.ApodDataSource
import com.morris.nasaimages.databinding.FragmentApodBinding
import com.morris.nasaimages.domain.apod.ApodRepository
import com.morris.nasaimages.presentation.ApodViewModel
import com.morris.nasaimages.presentation.ApodViewModelFactory
import com.morris.nasaimages.ui.mainfragment.MainAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApodFragment : Fragment(R.layout.fragment_apod), ApodAdapter.OnApodClickListener {

    private lateinit var binding: FragmentApodBinding

    private val viewModel by activityViewModels<ApodViewModel> {
        ApodViewModelFactory(
            ApodRepository(ApodDataSource(RetrofitClient.apodWebService(MainActivity.retrofitClient)))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApodBinding.bind(view)

        setRecyclerView()

        setObservers()
    }

    private fun setObservers() {

        if (viewModel.getApodData() == null) {

            viewModel.loadApod().observe(viewLifecycleOwner, { result ->

                when (result) {

                    is Resource.Loading -> {
                        binding.recyclerView.adapter = null
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.adapter = ApodAdapter(requireContext(), result.data, this)

                        viewModel.setApodData(result.data)
                    }
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        else {

            binding.recyclerView.adapter = ApodAdapter(requireContext(), viewModel.getApodData()!!, this)
        }
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onApodClick(item: Apod, position: Int) {

        val bundle = Bundle()
        bundle.putParcelable("param1", item)

       findNavController().navigate(R.id.action_apodFragment_to_apodDetailFragment, bundle)
    }
}