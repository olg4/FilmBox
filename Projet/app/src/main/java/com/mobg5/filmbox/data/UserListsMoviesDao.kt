package com.mobg5.filmbox.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobg5.filmbox.data.model.UserListMovies

@Dao
interface UserListsMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list_: UserListMovies)

    @Query("DELETE FROM list_movies_table")
    suspend fun deleteAll()

    @Query("DELETE FROM list_movies_table WHERE id = :listId_")
    suspend fun deleteList(listId_: Int)

    @Query("SELECT * FROM list_movies_table WHERE user_id = :userId_ ORDER BY list_title")
    fun getUserListsMovies(userId_: String): LiveData<List<UserListMovies>>

    @Query("SELECT * FROM list_movies_table")
    fun getAllListsMovies(): LiveData<List<UserListMovies>>
}