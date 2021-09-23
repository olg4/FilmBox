package com.mobg5.filmbox.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobg5.filmbox.data.model.ResultsMovies
import kotlinx.coroutines.launch


private const val TMDB_KEY = "***"

class HomeViewModel: ViewModel() {

    private val _navigateToSelectedMovie = MutableLiveData<ResultsMovies?>()
    val navigateToSelectedMovie: LiveData<ResultsMovies?>
        get() = _navigateToSelectedMovie

    private val _upcomingMovies = MutableLiveData<List<ResultsMovies>>()
    val upcomingMovies: LiveData<List<ResultsMovies>>
        get() = _upcomingMovies

    private val _popularMovies = MutableLiveData<List<ResultsMovies>>()
    val popularMovies: LiveData<List<ResultsMovies>>
        get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<ResultsMovies>>()
    val topRatedMovies: LiveData<List<ResultsMovies>>
        get() = _topRatedMovies

    private val _nowPlayingMovies = MutableLiveData<List<ResultsMovies>>()
    val nowPlayingMovies: LiveData<List<ResultsMovies>>
        get() = _nowPlayingMovies

    init {
        getTmdbUpcomingMovies()
        getTmdbNowPlayingMovies()
        getTmdbTopRatedMovies()
        getTmdbPopularMovies()
    }

    private fun getTmdbUpcomingMovies() {
        viewModelScope.launch {
            try {
                _upcomingMovies.value = TmdbApi.retrofitService.getUpcomingMovies(TMDB_KEY).results
            } catch (e: Exception) {
                _upcomingMovies.value = ArrayList()
            }
        }
    }

    private fun getTmdbPopularMovies() {
        viewModelScope.launch {
            try {
                _popularMovies.value = TmdbApi.retrofitService.getPopularMovies(TMDB_KEY).results
            } catch (e: Exception) {

            }
        }
    }

    private fun getTmdbTopRatedMovies() {
        viewModelScope.launch {
            try {
                _topRatedMovies.value = TmdbApi.retrofitService.getTopRatedMovies(TMDB_KEY).results
            } catch (e: Exception) {

            }
        }
    }

    private fun getTmdbNowPlayingMovies() {
        viewModelScope.launch {
            try {
                _nowPlayingMovies.value = TmdbApi.retrofitService.getNowPlayingMovies(TMDB_KEY).results
            } catch (e: Exception) {

            }
        }
    }

    fun displayMovieDetails(movieDetails: ResultsMovies) {
        _navigateToSelectedMovie.value = movieDetails
    }

    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value =  null
    }
}
