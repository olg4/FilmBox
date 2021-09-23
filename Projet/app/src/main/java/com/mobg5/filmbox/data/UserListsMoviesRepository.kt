package com.mobg5.filmbox.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.mobg5.filmbox.data.model.UserListMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.IO

class UserListsMoviesRepository {

    private var userListsMoviesDatabase: UserListsMoviesDatabase? = null

    private fun initializeDB(context: Context): UserListsMoviesDatabase {
        return UserListsMoviesDatabase.getInstance(context)
    }

    fun addList(context: Context, title: String, user: String) {
        userListsMoviesDatabase = initializeDB(context)

        CoroutineScope(IO).launch {
            val userListMovies = UserListMovies(user = user, title = title)
            userListsMoviesDatabase!!.listsMoviesDao.insert(userListMovies)
        }
    }

    fun getUserListsMovie(context: Context, user: String): LiveData<List<UserListMovies>> {
        userListsMoviesDatabase = initializeDB(context)
        return userListsMoviesDatabase!!.listsMoviesDao.getUserListsMovies(user)
    }
}