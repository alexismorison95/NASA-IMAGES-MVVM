package com.morris.nasaimages.ui.apod

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.morris.nasaimages.R
import com.morris.nasaimages.application.MainActivity
import com.morris.nasaimages.application.RetrofitClient
import com.morris.nasaimages.data.model.apod.Apod
import com.morris.nasaimages.data.remote.apod.ApodDataSource
import com.morris.nasaimages.databinding.FragmentApodDetailBinding
import com.morris.nasaimages.domain.apod.ApodRepository
import com.morris.nasaimages.presentation.ApodViewModel
import com.morris.nasaimages.presentation.ApodViewModelFactory
import com.squareup.picasso.Picasso

class ApodDetailFragment : Fragment(R.layout.fragment_apod_detail) {

    private lateinit var binding: FragmentApodDetailBinding

    private val viewModel by activityViewModels<ApodViewModel> {
        ApodViewModelFactory(
            ApodRepository(ApodDataSource(RetrofitClient.apodWebService(MainActivity.retrofitClient)))
        )
    }

    private var apodItem: Apod? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            apodItem = it.getParcelable("param1")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApodDetailBinding.bind(view)

        Picasso.get()
            .load(apodItem!!.url)
            .into(binding.image)

        binding.title.text = apodItem!!.title
        binding.copyright.text = apodItem!!.copyright
        binding.date.text = apodItem!!.date
        binding.explanation.text = apodItem!!.explanation
    }

}