package com.tab.countries.presentation.countries

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tab.countries.R
import com.tab.countries.databinding.FragmentCountryListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountryListFragment : Fragment() {

    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CountryListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCountryListBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
    }

    private fun setupRecyclerView() {
        with(binding) {
            countryRecyclerView.adapter = CountryListAdapter()
            countryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { viewState ->
                    handleViewState(viewState)
                }
            }
        }
    }

    private fun handleViewState(viewState: CountryListViewModel.ViewState) {
        with(binding) {
            progress.isVisible = viewState.isLoading
            countryRecyclerView.isVisible = !viewState.isLoading
            countryListAdapter.submitList(viewState.countryList)
            if (viewState.isError) showErrorDialog()
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.load_country_list_error_dialog_title)
            .setMessage(R.string.generic_error_message)
            .setPositiveButton(R.string.try_again) { _, _ ->
                viewModel.getCountryList()
            }
            .create()
            .show()
    }
}

private val FragmentCountryListBinding.countryListAdapter: CountryListAdapter
    get() = (countryRecyclerView.adapter as CountryListAdapter)
