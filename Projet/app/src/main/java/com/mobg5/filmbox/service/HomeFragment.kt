package com.mobg5.filmbox.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobg5.filmbox.R
import com.mobg5.filmbox.adapter.NowPlayingMovieGridAdapter
import com.mobg5.filmbox.adapter.PopularMovieGridAdapter
import com.mobg5.filmbox.adapter.TopRatedMovieGridAdapter
import com.mobg5.filmbox.adapter.UpcomingMovieGridAdapter
import com.mobg5.filmbox.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private lateinit var gridLayoutManagerUpcoming: GridLayoutManager
    private lateinit var gridLayoutManagerPopular: GridLayoutManager
    private lateinit var gridLayoutManagerTopRated: GridLayoutManager
    private lateinit var gridLayoutManagerNowPlaying: GridLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        gridLayoutManagerUpcoming = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        gridLayoutManagerNowPlaying = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        gridLayoutManagerTopRated = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        gridLayoutManagerPopular = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

        val binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.root.findViewById<RecyclerView>(R.id.movies_horizontal_scroll).layoutManager = gridLayoutManagerUpcoming
        binding.root.findViewById<RecyclerView>(R.id.movies_horizontal_scroll_now_playing).layoutManager = gridLayoutManagerNowPlaying
        binding.root.findViewById<RecyclerView>(R.id.movies_horizontal_scroll_popular).layoutManager = gridLayoutManagerPopular
        binding.root.findViewById<RecyclerView>(R.id.movies_horizontal_scroll_top_rated).layoutManager = gridLayoutManagerTopRated

        binding.moviesHorizontalScroll.adapter = UpcomingMovieGridAdapter(
            UpcomingMovieGridAdapter.OnClickListener { upcoming ->
            viewModel.displayMovieDetails(upcoming)
        })

        binding.moviesHorizontalScrollNowPlaying.adapter = NowPlayingMovieGridAdapter(
            NowPlayingMovieGridAdapter.OnClickListener { nowPlaying ->
                viewModel.displayMovieDetails(nowPlaying)
            })

        binding.moviesHorizontalScrollTopRated.adapter = TopRatedMovieGridAdapter(
            TopRatedMovieGridAdapter.OnClickListener { topRated ->
                viewModel.displayMovieDetails(topRated)
            })

        binding.moviesHorizontalScrollPopular.adapter = PopularMovieGridAdapter(
            PopularMovieGridAdapter.OnClickListener { popular ->
            viewModel.displayMovieDetails(popular)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, {
            if(null != it) {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionShowDetails(it))
                viewModel.displayMovieDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }
}
