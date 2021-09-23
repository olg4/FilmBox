package com.mobg5.filmbox.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobg5.filmbox.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val application = requireNotNull(activity).application
        val binding = FragmentMovieDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val selected = MovieDetailsFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val viewModelFactory = MovieDetailsViewModelFactory(selected, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
        binding.viewModel = viewModel

        binding.addToListButton.setOnClickListener {
            viewModel.addMovie()
        }

        viewModel.addMovie.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(MovieDetailsFragmentDirections.actionAddToListFragment(it))
                viewModel.addMovieComplete()
            }
        })

        return binding.root
    }
}