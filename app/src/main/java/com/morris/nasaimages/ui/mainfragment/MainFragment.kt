package com.morris.nasaimages.ui.mainfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.data.local.main.MainDataSource
import com.morris.nasaimages.data.model.main.MainItem
import com.morris.nasaimages.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main), MainAdapter.OnMainClickListener {

    private lateinit var binding: FragmentMainBinding

    private val mainDataSource = MainDataSource()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        setRecyclerView()
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = MainAdapter(requireContext(), mainDataSource.getMainItems(), this)
    }

    override fun onMainClick(item: MainItem, position: Int) {

        findNavController().navigate(R.id.action_mainFragment_to_apodFragment)
    }
}