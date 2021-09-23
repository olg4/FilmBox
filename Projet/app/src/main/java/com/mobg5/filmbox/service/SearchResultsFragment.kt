package com.mobg5.filmbox.service

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobg5.filmbox.adapter.SearchResultsGridAdapter
import com.mobg5.filmbox.databinding.FragmentSearchResultsBinding

class SearchResultsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val results = SearchResultsFragmentArgs.fromBundle(requireArguments()).results
        val viewModelFactory = SearchResultsViewModelFactory(results)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SearchResultsViewModel::class.java)

        val binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.resultsLists.adapter = SearchResultsGridAdapter(SearchResultsGridAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, {
            if(null != it) {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.resultsLists.windowToken, 0)
                this.findNavController().navigate(
                    SearchResultsFragmentDirections.actionShowDetails(it))
                viewModel.displayMovieDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

}