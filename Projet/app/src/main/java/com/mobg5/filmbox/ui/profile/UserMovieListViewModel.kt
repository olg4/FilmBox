package com.mobg5.filmbox.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobg5.filmbox.data.MoviesRepository
import com.mobg5.filmbox.data.model.Movies
import com.mobg5.filmbox.data.model.ResultsMovies
import com.mobg5.filmbox.data.model.UserListMovies
import com.mobg5.filmbox.service.TmdbApi
import kotlinx.coroutines.launch

private const val TMDB_KEY = "***"

class UserMovieListViewModel(private val moviesRepository: MoviesRepository,
                             private val selectedList: UserListMovies): ViewModel() {

    private lateinit var _moviesListById: LiveData<List<Movies>>
    val moviesListById: LiveData<List<Movies>>
        get() = _moviesListById

    private val _moviesList = MutableLiveData<List<ResultsMovies>>()
    val moviesList: LiveData<List<ResultsMovies>>
        get () = _moviesList

    private val result = mutableListOf<ResultsMovies>()

    private val _navigateToSelectedMovie = MutableLiveData<ResultsMovies?>()
    val navigateToSelectedMovie: LiveData<ResultsMovies?>
        get() = _navigateToSelectedMovie

    fun setMovieList(context: Context) {
        _moviesListById = moviesRepository.getMovies(context, selectedList.id)
    }

    fun getMovies(listById: List<Movies>) {
        for (element in listById) {
            getATmdbMovie(element.movie_id, listById.size)
        }
    }

    private fun getATmdbMovie(id: Int, nbMovies: Int) {

        viewModelScope.launch {
            try {
                result.add(TmdbApi.retrofitService.getAMovie(id, TMDB_KEY))
                // to avoid a synchronization problem
                if (result.size == nbMovies) _moviesList.value  = result
            } catch (e: Exception) {
                _moviesList.value = ArrayList()
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
