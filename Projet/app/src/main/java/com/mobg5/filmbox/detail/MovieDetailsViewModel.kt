package com.mobg5.filmbox.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobg5.filmbox.data.model.ResultsMovies

class MovieDetailsViewModel (resultsMovies: ResultsMovies,
                             app: Application): AndroidViewModel(app) {

    private val _selectedMovie = MutableLiveData<ResultsMovies>()
    val selectedMovie: LiveData<ResultsMovies>
        get() = _selectedMovie

    private val _addMovie = MutableLiveData<ResultsMovies?>()
    val addMovie: LiveData<ResultsMovies?>
        get() = _addMovie

    init {
        _selectedMovie.value = resultsMovies
    }

    fun addMovie() {
        _addMovie.value = _selectedMovie.value
    }

    fun addMovieComplete() {
        _addMovie.value =  null
    }
}