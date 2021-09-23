package com.mobg5.filmbox.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobg5.filmbox.data.UserListsMoviesRepository

class UserCreateMovieListViewModel(private val repository: UserListsMoviesRepository): ViewModel() {

    private val _titleList = MutableLiveData<String>()
    val titleList: LiveData<String>
        get() = _titleList

    fun storeNewList(context: Context, title: String, user: String) {
        repository.addList(context, title, user)
    }

}