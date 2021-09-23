package com.mobg5.filmbox.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobg5.filmbox.databinding.FragmentProfileBinding

class ProfileFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentProfileBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.seeListsButton.setOnClickListener {
            this.findNavController().navigate(ProfileFragmentDirections.actionShowUserMovieLists())
        }

        setHasOptionsMenu(true)
        return binding.root
    }
}