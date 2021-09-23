package com.mobg5.filmbox.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobg5.filmbox.data.model.ResultsMovies

class MovieDetailsViewModelFactory (
    private val resultsMovies: ResultsMovies,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(resultsMovies, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}