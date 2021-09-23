package com.mobg5.filmbox.service

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobg5.filmbox.R
import com.mobg5.filmbox.adapter.ExploreMoviesGridAdapter
import com.mobg5.filmbox.databinding.FragmentExploreBinding

class ExploreFragment: Fragment() {

    private val viewModel: ExploreViewModel by lazy {
        ViewModelProvider(this).get(ExploreViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentExploreBinding.inflate(inflater, container, false)
        var maxPage = 0
        var pageCounter = 1

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        if (pageCounter == 1) binding.root.findViewById<ImageButton>(R.id.explore_previous_page).visibility = View.INVISIBLE

        /*
        * movie's details
         */
        binding.moviesGrid.adapter = ExploreMoviesGridAdapter(ExploreMoviesGridAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        /*
        Based on the TMDb API Doc, it would be 500 pages max
         */
        viewModel.totalPages.observe(viewLifecycleOwner, {
            maxPage = it
        })

        /*
         to next page
         ------------

         1000 is the max page in TMDB
         */
        binding.root.findViewById<ImageButton>(R.id.explore_next_page).setOnClickListener {
            if (pageCounter < maxPage) {
                pageCounter ++
                if (pageCounter == 2) binding.root.findViewById<ImageButton>(R.id.explore_previous_page).visibility = View.VISIBLE
                else if (pageCounter == maxPage) binding.root.findViewById<ImageButton>(R.id.explore_next_page).visibility = View.INVISIBLE
                viewModel.toNextPage(pageCounter)
            }
        }

        /*
        * to previous page
        * ---------------
        */
        binding.root.findViewById<ImageButton>(R.id.explore_previous_page).setOnClickListener {
            if (pageCounter > 1) {
                pageCounter --
                if (pageCounter == (maxPage-1)) binding.root.findViewById<ImageButton>(R.id.explore_next_page).visibility = View.VISIBLE
                else if (pageCounter == 1) binding.root.findViewById<ImageButton>(R.id.explore_previous_page).visibility = View.INVISIBLE
                viewModel.toPreviousPage(pageCounter)
            }
        }

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, {
            if(null != it) {
                this.findNavController().navigate(
                    ExploreFragmentDirections.actionShowDetails(it))
                viewModel.displayMovieDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

}