package com.mobg5.filmbox.ui.profile

import android.content.Context
import androidx.lifecycle.*
import com.mobg5.filmbox.data.UserListsMoviesRepository
import com.mobg5.filmbox.data.model.UserListMovies


class UserMovieListsViewModel (private val userListsMoviesRepository: UserListsMoviesRepository) : ViewModel() {

    private lateinit var _userMovieLists: LiveData<List<UserListMovies>>
    val userMovieLists: LiveData<List<UserListMovies>>
        get() = _userMovieLists

    private val _selectedList = MutableLiveData<UserListMovies?>()
    val selectedList: LiveData<UserListMovies?>
        get() = _selectedList

    fun getUserMovieLists(context: Context, logged: String) {
        _userMovieLists = userListsMoviesRepository.getUserListsMovie(context, logged)
    }

    fun displayMoviesInList(list: UserListMovies) {
        _selectedList.value = list
    }

    fun displayMoviesInListComplete() {
        _selectedList.value = null
    }
}