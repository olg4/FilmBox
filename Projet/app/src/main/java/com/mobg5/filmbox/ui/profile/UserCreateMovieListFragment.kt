package com.mobg5.filmbox.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobg5.filmbox.databinding.FragmentUserCreateListBinding

class UserCreateMovieListFragment: Fragment() {

    private val viewModel: UserCreateMovieListViewModel by lazy {
        ViewModelProvider(this, UserCreateMovieListViewModelFactory()).get(UserCreateMovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentUserCreateListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val sharedPref = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val username = sharedPref?.getString("username", "")

        binding.fabCreateList.setOnClickListener {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.fabCreateList.windowToken, 0)
            val getTitle = binding.listTitle.text.toString()
            viewModel.storeNewList(requireContext(), getTitle, username.toString())
            this.findNavController().navigate(UserCreateMovieListFragmentDirections.actionHostToProfile())
        }


        return binding.root
    }
}