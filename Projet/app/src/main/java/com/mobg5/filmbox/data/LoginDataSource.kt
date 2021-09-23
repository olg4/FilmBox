package com.mobg5.filmbox.data

import com.mobg5.filmbox.data.model.LoggedInUser
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username_: String): Result<LoggedInUser> {
        return try {
            val calendar: Calendar = Calendar.getInstance()
            val date: Date = calendar.time
            val user = LoggedInUser(username = username_, lastConnectionDateTime = date)
            Result.Success(user)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}