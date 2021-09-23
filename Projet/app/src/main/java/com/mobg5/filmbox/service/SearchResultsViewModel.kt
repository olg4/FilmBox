package com.mobg5.filmbox.service

import androidx.lifecycle.*
import com.mobg5.filmbox.data.model.ListResultsMovies
import com.mobg5.filmbox.data.model.ResultsMovies

class SearchResultsViewModel(searchResults: ListResultsMovies): ViewModel() {

    private val _results = MutableLiveData<List<ResultsMovies>>()
    val results: LiveData<List<ResultsMovies>>
        get() = _results

    private val _navigateToSelectedMovie = MutableLiveData<ResultsMovies?>()
    val navigateToSelectedMovie: LiveData<ResultsMovies?>
        get() = _navigateToSelectedMovie

    init {
        _results.value = searchResults.results
    }

    fun displayMovieDetails(result: ResultsMovies) {
        _navigateToSelectedMovie.value = result
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value =  null
    }
}