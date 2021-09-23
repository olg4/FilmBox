package com.mobg5.filmbox.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobg5.filmbox.data.MoviesRepository
import com.mobg5.filmbox.data.UserListsMoviesRepository
import com.mobg5.filmbox.data.model.ResultsMovies

class AddToListViewModelFactory(private val movieToAdd: ResultsMovies): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddToListViewModel::class.java)) {
            return AddToListViewModel(
                moviesRepository = MoviesRepository(),
                userListsMoviesRepository = UserListsMoviesRepository(),
                theMovie = movieToAdd
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}