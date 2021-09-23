package com.mobg5.filmbox.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.mobg5.filmbox.data.model.LoggedInUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.IO
import java.util.*

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an UserListsMoviesDao-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // UserListsMoviesDao-memory cache of the loggedInUser object
    private var user: LoggedInUser? = null
    private var userDatabase: UserDatabase? = null

    /*
    val isLoggedIn: Boolean
        get() = user != null*/

    init {
        // If user credentials will be cached UserListsMoviesDao local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    private fun initializeDB(context: Context): UserDatabase {
        return UserDatabase.getInstance(context)
    }


    fun addUser(context: Context, username: String, date: Date) {
        userDatabase = initializeDB(context)

        CoroutineScope(IO).launch {
            val loggedInUser = LoggedInUser(username = username, lastConnectionDateTime = date)
            userDatabase!!.userDatabaseDao.insert(loggedInUser)
        }
    }

    fun isUserExists(context: Context, username: String): LiveData<Boolean> {
        userDatabase = initializeDB(context)
        val t = userDatabase!!.userDatabaseDao.isUserExists(username)
        Log.d("login REPO: ", t.value.toString())
        return t
    }

    fun updateUser(context: Context, username: String, date: Date) {
        userDatabase = initializeDB(context)
        CoroutineScope(IO).launch {
            userDatabase!!.userDatabaseDao.updateUserLastConnectionDatetime(username, date)
        }
    }

    fun getAllUsers(context: Context): LiveData<List<LoggedInUser>> {
        userDatabase = initializeDB(context)
        return userDatabase!!.userDatabaseDao.getAllUsers()
    }


    /*
    fun logout() {
        user = null
        dataSource.logout()
    }*/

    fun login(username: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached UserListsMoviesDao local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}