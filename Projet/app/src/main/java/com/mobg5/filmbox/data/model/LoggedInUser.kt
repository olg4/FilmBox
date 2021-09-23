package com.mobg5.filmbox.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * Data class that captures user information for logged UserListsMoviesDao users retrieved from LoginRepository
 * The email is an id itself but adding the userId for now is used to make some tests
 */
@Entity(tableName = "user_table")
data class LoggedInUser (
        @PrimaryKey
    var username: String,
        @ColumnInfo(name = "date_of_registration")
    var registrationDate: Date = Calendar.getInstance().time,
        @ColumnInfo(name = "last_connection")
    var lastConnectionDateTime: Date
)