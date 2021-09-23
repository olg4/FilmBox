package com.mobg5.filmbox.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobg5.filmbox.adapter.UserAllMovieListAdapter
import com.mobg5.filmbox.databinding.FragmentUserAllMovieListBinding

class UserAllMovieListFragment: Fragment() {

    private val viewModel: UserMovieListsViewModel by lazy {
        ViewModelProvider(this, UserAllMovieListViewModelFactory()).get(UserMovieListsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val sharedPref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val username = sharedPref?.getString("username", "")
        viewModel.getUserMovieLists(requireContext(), username.toString())

        val binding = FragmentUserAllMovieListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.moviesLists.adapter = UserAllMovieListAdapter(UserAllMovieListAdapter.OnClickListener {
           viewModel.displayMoviesInList(it)
        })

        viewModel.userMovieLists.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                this.findNavController().navigate(
                    UserAllMovieListFragmentDirections.actionHostToCreate())
            }
        })

        viewModel.selectedList.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(
                    UserAllMovieListFragmentDirections.actionShowMoviesList(it))
                viewModel.displayMoviesInListComplete()
            }
        })

        return binding.root
    }
}
