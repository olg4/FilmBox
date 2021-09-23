package com.mobg5.filmbox.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.mobg5.filmbox.adapter.AddToListGridAdapter
import com.mobg5.filmbox.databinding.FragmentAddToListBinding

class AddToListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val sharedPref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val username = sharedPref?.getString("username", "")

        val movieToAdd = AddToListFragmentArgs.fromBundle(requireArguments()).movieToAdd
        val viewModelFactory = AddToListViewModelFactory(movieToAdd)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(AddToListViewModel::class.java)

        viewModel.getUserMovieLists(requireContext(), username.toString())

        viewModel.userMovieLists.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "You haven't created a list yet",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        val binding = FragmentAddToListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        var listId = 0
        var movieId: Int
        var selected: Boolean

        binding.moviesLists.adapter = AddToListGridAdapter(AddToListGridAdapter.OnClickListener { list ->
            listId = list.id
            viewModel.setAddMovie()
        })


        viewModel.movieToAdd.observe(viewLifecycleOwner, { movie ->
            movieId = movie.id
            selected = true

            /*
            Adds the movie in the list or removes if it's already in it when the user
            (re)selects the list
             */
            val isExist: LiveData<Boolean> = viewModel.isExist(requireContext(), listId, movieId)
            isExist.observe(viewLifecycleOwner, { exists ->
                if (exists) viewModel.setExists(exists, listId)
                if (selected) {
                    if (!exists) {
                        Toast.makeText(
                            requireContext(),
                            "Movie added!",
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.addMovie(requireContext(), listId, movieId)
                    } else {
                        viewModel.removeMovie(listId, movieId)
                        Toast.makeText(
                            requireContext(),
                            "This movie was already in the list, so you delete it\nPress again to revert",
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.removeMovie(listId, movieId)
                    }
                    selected = false
                }
            })
        })

        return binding.root
    }
}