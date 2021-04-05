package com.morris.nasaimages.modules.library.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.morris.nasaimages.R
import com.morris.nasaimages.databinding.FragmentLibraryFormBinding
import com.morris.nasaimages.modules.library.data.model.QueryLibrary
import java.time.Year
import java.util.*


class LibraryFormFragment : Fragment(R.layout.fragment_library_form) {

    private lateinit var binding: FragmentLibraryFormBinding

    private var query = ""
    private var startYear = 1920
    private var endYear = Year.now().value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLibraryFormBinding.bind(view)

        initData()

        setListeners()
    }

    private fun initData() {

        binding.inputStartYear.setText("1920")
        binding.inputEndYear.setText(endYear.toString())

        binding.sliderYears.valueFrom = 1920.0F
        binding.sliderYears.valueTo = endYear.toFloat()
        binding.sliderYears.values = listOf(1920.0F, endYear.toFloat())

    }

    private fun setListeners() {

        // Search by button
        binding.btnSearch.setOnClickListener {

            search()
        }

        // Search by input
        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                search()
            }

            true
        }

        // Slider changes
        binding.sliderYears.addOnChangeListener { slider, _, _ ->

            startYear = slider.values[0].toInt()
            endYear = slider.values[1].toInt()

            binding.inputStartYear.setText(startYear.toString())
            binding.inputEndYear.setText(endYear.toString())
        }

        // Years change from inputs
        binding.inputStartYear.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.isNotEmpty()) {

                    if (s.toString().toInt() > 1920) {

                        startYear = s.toString().toInt()

                        binding.sliderYears.values = listOf(startYear.toFloat(), binding.sliderYears.values[1])

                        binding.inputStartYear.setSelection(binding.inputStartYear.length())
                    }
                }
            }
        })

        binding.inputEndYear.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s!!.isNotEmpty()) {

                    if (s.toString().toInt() > 1920) {

                        endYear = s.toString().toInt()

                        binding.sliderYears.values = listOf(binding.sliderYears.values[0], endYear.toFloat())

                        binding.inputEndYear.setSelection(binding.inputEndYear.length())
                    }
                }
            }
        })
    }

    private fun search() {

        val queryLibrary = QueryLibrary(
            binding.searchInput.text.toString().toLowerCase(Locale.getDefault()),
            startYear.toString(),
            endYear.toString()
        )

        val bundle = Bundle()
        bundle.putParcelable("param1", queryLibrary)

        findNavController().navigate(R.id.action_libraryFormFragment_to_libraryResultsFragment, bundle)
    }
}