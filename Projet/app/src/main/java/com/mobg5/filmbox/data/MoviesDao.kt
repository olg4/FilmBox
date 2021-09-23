package com.mobg5.filmbox.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobg5.filmbox.data.model.Movies

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie_: Movies)

    @Query("DELETE FROM movies_table WHERE list_id = :listId AND movie_id = :movie_")
    suspend fun delete(movie_: Int, listId: Int)

    @Query("SELECT * FROM movies_table WHERE list_id = :list_")
    fun getMovies(list_: Int): LiveData<List<Movies>>

    @Query("SELECT EXISTS(SELECT * FROM movies_table WHERE list_id = :listId AND movie_id = :movie_)")
    fun isExist(movie_: Int, listId: Int): LiveData<Boolean>

}