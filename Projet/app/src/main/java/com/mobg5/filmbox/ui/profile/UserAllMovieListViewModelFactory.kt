package com.mobg5.filmbox.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobg5.filmbox.data.UserListsMoviesRepository

class UserAllMovieListViewModelFactory: ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserMovieListsViewModel::class.java)) {
                return UserMovieListsViewModel(
                    userListsMoviesRepository = UserListsMoviesRepository()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}