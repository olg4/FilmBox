package com.mobg5.filmbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobg5.filmbox.data.model.ListResultsMovies
import com.mobg5.filmbox.service.TmdbApi
import kotlinx.coroutines.launch

private const val TMDB_KEY = "***"

class MainViewModel: ViewModel() {

    private val _results = MutableLiveData<ListResultsMovies>()
    val results: LiveData<ListResultsMovies>
        get() = _results

    fun searchInTmdb(input: String) {
        viewModelScope.launch {
            try {
                _results.value = TmdbApi.retrofitService.search(TMDB_KEY, input)
            } catch (e: Exception) {

            }
        }
    }
}
