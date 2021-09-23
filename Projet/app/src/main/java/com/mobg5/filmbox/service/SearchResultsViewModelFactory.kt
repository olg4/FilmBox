package com.mobg5.filmbox.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobg5.filmbox.data.model.ListResultsMovies

class SearchResultsViewModelFactory(
        private val results: ListResultsMovies) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultsViewModel::class.java)) {
            return SearchResultsViewModel(results) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}