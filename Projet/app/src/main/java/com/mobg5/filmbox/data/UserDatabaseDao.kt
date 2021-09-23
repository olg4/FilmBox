package com.mobg5.filmbox.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mobg5.filmbox.data.model.LoggedInUser
import java.util.Date

@Dao
interface UserDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: LoggedInUser)

    @Query("UPDATE user_table SET last_connection = :date_ WHERE username = :username_")
    fun updateUserLastConnectionDatetime(username_: String, date_: Date)

    @Query("SELECT * from user_table WHERE username = :username_")
    fun getUser(username_: String?): LiveData<LoggedInUser>

    @Query("SELECT * from user_table WHERE date_of_registration = :registrationDate_ ORDER BY username")
    fun get(registrationDate_: Date?): LiveData<List<LoggedInUser>>

    @Query("SELECT EXISTS(SELECT * from user_table WHERE username = :username_)")
    fun isUserExists(username_: String?): LiveData<Boolean>

    @Query("DELETE FROM user_table")
    fun clear()

    @Query("SELECT * FROM user_table ORDER BY username")
    fun getAllUsers(): LiveData<List<LoggedInUser>>
}