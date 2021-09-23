package com.mobg5.filmbox.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobg5.filmbox.data.UserListsMoviesRepository

class UserCreateMovieListViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserCreateMovieListViewModel::class.java)) {
            return UserCreateMovieListViewModel(
                repository = UserListsMoviesRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}