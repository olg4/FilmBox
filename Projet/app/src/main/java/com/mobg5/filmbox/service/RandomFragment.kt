package com.mobg5.filmbox.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobg5.filmbox.databinding.FragmentRandomBinding
import kotlin.random.Random

class RandomFragment: Fragment() {
    private val viewModel: RandomViewModel by lazy {
        ViewModelProvider(this).get(RandomViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentRandomBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.randomButton.setOnClickListener {
            val randomPage = Random.nextInt(1, 501)
            val randomMovie = Random.nextInt(0, 20)
            binding.infoRandomText.visibility = View.INVISIBLE
            viewModel.getTmdbMovies(randomPage, randomMovie)
        }

        viewModel.response.observe(viewLifecycleOwner, {
            binding.movie = it
        })

        binding.randomImage.setOnClickListener {

            if (null != it && viewModel.imageDisplayed) {
                this.findNavController().navigate(
                    RandomFragmentDirections.actionShowDetails(viewModel.selectedMovie))
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }
}