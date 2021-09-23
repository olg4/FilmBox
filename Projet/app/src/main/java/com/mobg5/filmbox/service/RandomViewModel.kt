package com.mobg5.filmbox.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobg5.filmbox.data.model.ResultsMovies
import kotlinx.coroutines.launch

private const val TMDB_KEY = "***"

class RandomViewModel: ViewModel() {

    private val _response = MutableLiveData<ResultsMovies>()
    val response: LiveData<ResultsMovies>
        get() = _response

    var imageDisplayed = false

    lateinit var selectedMovie: ResultsMovies

     fun getTmdbMovies(page: Int, movie: Int) {
        viewModelScope.launch {
            try {
                val listResult = TmdbApi.retrofitService.getMovies(TMDB_KEY, page)
                _response.value = listResult.results[movie]
                selectedMovie = listResult.results[movie]
                imageDisplayed = true
            } catch (e: Exception) {
            }
        }
    }

}
