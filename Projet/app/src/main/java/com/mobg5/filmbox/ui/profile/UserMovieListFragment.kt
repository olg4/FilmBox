package com.mobg5.filmbox.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobg5.filmbox.adapter.UserMoviesListAdapter
import com.mobg5.filmbox.databinding.FragmentUserMovieListBinding

class UserMovieListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val list = UserMovieListFragmentArgs.fromBundle(requireArguments()).selectedList
        val viewModelFactory = UserMovieListViewModelFactory(list)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(UserMovieListViewModel::class.java)

        viewModel.setMovieList(requireContext())

        val binding = FragmentUserMovieListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel.moviesListById.observe(viewLifecycleOwner, {
            viewModel.getMovies(it)
        })

        binding.viewModel = viewModel

        binding.moviesList.adapter = UserMoviesListAdapter(UserMoviesListAdapter.OnClickListener {
            viewModel.displayMovieDetails(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, {
            if(null != it) {
                this.findNavController().navigate(
                    UserMovieListFragmentDirections.actionShowDetails(it))
                viewModel.displayMovieDetailsComplete()
            }
        })

        return binding.root
    }
}