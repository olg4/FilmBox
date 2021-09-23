package com.mobg5.filmbox.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobg5.filmbox.data.MoviesRepository
import com.mobg5.filmbox.data.UserListsMoviesRepository
import com.mobg5.filmbox.data.model.ResultsMovies
import com.mobg5.filmbox.data.model.UserListMovies

class AddToListViewModel(private val moviesRepository: MoviesRepository,
                         private val userListsMoviesRepository: UserListsMoviesRepository,
                         private val theMovie: ResultsMovies): ViewModel() {

    private val _movieToAdd = MutableLiveData<ResultsMovies>()
    val movieToAdd: LiveData<ResultsMovies>
        get() = _movieToAdd

    private lateinit var _userMovieLists: LiveData<List<UserListMovies>>
    val userMovieLists: LiveData<List<UserListMovies>>
        get() = _userMovieLists

    private val _exists = MutableLiveData<Boolean>()
    val exists: LiveData<Boolean>
        get() = _exists

    fun getUserMovieLists(context: Context, logged: String): LiveData<List<UserListMovies>> {
        _userMovieLists = userListsMoviesRepository.getUserListsMovie(context, logged)
        return _userMovieLists
    }

    fun setExists(state: Boolean, list: Int) {
        _exists.value = state
    }

   fun isExist(context: Context, listId: Int, movieId: Int): LiveData<Boolean> {
       return moviesRepository.isExist(context, listId, movieId)
   }

    fun removeMovie(list: Int, movie: Int) {
        moviesRepository.removeMovie(list, movie)
    }

    fun addMovie(context: Context, list: Int, movie: Int) {
        moviesRepository.addMovie(context, list, movie)
    }

    fun setAddMovie() {
        _movieToAdd.value = theMovie
    }
}