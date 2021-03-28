package com.morris.nasaimages.modules.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.nasaimages.R
import com.morris.nasaimages.modules.main.data.local.MainDataSource
import com.morris.nasaimages.modules.main.data.model.MainItem
import com.morris.nasaimages.databinding.FragmentMainBinding
import com.morris.nasaimages.modules.main.repository.MainRepository

class MainFragment : Fragment(R.layout.fragment_main), MainAdapter.OnMainClickListener {

    private lateinit var binding: FragmentMainBinding

    private val mainDataSource = MainDataSource()

    private val repository = MainRepository(mainDataSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        setRecyclerView()
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val result = repository.getMainItems()

        binding.recyclerView.adapter = MainAdapter(requireContext(), result, this)
    }

    override fun onMainClick(item: MainItem, position: Int) {

        findNavController().navigate(R.id.action_mainFragment_to_apodFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.menuFavs -> {

                findNavController().navigate(R.id.action_mainFragment_to_favouritesFragment)
                true
            }
            R.id.settingsMenu -> {

                true
            }
            else -> {
                // About
                true
            }
        }
    }
}