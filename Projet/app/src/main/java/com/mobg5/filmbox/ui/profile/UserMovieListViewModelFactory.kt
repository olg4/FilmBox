package com.mobg5.filmbox.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobg5.filmbox.data.MoviesRepository
import com.mobg5.filmbox.data.model.UserListMovies

class UserMovieListViewModelFactory(private val selectedList: UserListMovies): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserMovieListViewModel::class.java)) {
            return UserMovieListViewModel(
                moviesRepository = MoviesRepository(),
                selectedList = selectedList
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}