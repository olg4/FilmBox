package com.mobg5.filmbox.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobg5.filmbox.data.model.ListResultsMovies
import com.mobg5.filmbox.data.model.ResultsMovies
import kotlinx.coroutines.launch

private const val TMDB_KEY = "***"

class ExploreViewModel: ViewModel() {

    private lateinit var _aTempPage: ListResultsMovies

    private var _n = MutableLiveData<Int>()
    val n: LiveData<Int>
        get() = _n

    private var _aPage = MutableLiveData<ListResultsMovies>()
    val aPage: LiveData<ListResultsMovies>
        get() = _aPage

    private var _totalResults = MutableLiveData<Int>()
    val totalResults: LiveData<Int>
            get() = _totalResults

    /* A value is given just one time in the GetTmdbMovies function when the first page is requested
    */
    private var _totalPages = MutableLiveData<Int>()
    val totalPages: LiveData<Int>
        get() = _totalPages

    private val _navigateToSelectedMovie = MutableLiveData<ResultsMovies?>()
    val navigateToSelectedMovie: LiveData<ResultsMovies?>
        get() = _navigateToSelectedMovie


    private val _moviesResults = MutableLiveData<List<ResultsMovies>>()
    val moviesResults: LiveData<List<ResultsMovies>>
        get() = _moviesResults

    init {
        _n.value = 1
        getTmdbMovies(1)
    }

    private fun getTmdbMovies(page: Int) {
        viewModelScope.launch {
            try {
                _aTempPage = TmdbApi.retrofitService.getMovies(TMDB_KEY, page)
                _totalResults.value = _aTempPage.totalResults
                if (page == 1) _totalPages.value = _aTempPage.totalPages
                _aPage.value = _aTempPage
                _moviesResults.value = _aTempPage.results
            } catch (e: Exception) {
                _moviesResults.value = ArrayList()
            }
        }
    }

    fun toNextPage(page: Int) {
        getTmdbMovies(page)
        _n.value = page
    }

    fun toPreviousPage(page: Int) {
        getTmdbMovies(page)
        _n.value = page
    }

    fun displayMovieDetails(movieDetails: ResultsMovies) {
        _navigateToSelectedMovie.value = movieDetails
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value =  null
    }

}
