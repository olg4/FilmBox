package com.mobg5.filmbox.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.mobg5.filmbox.data.model.Movies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.IO

class MoviesRepository {

    private var moviesDatabase: MoviesDatabase? = null

    private fun initializeDB(context: Context): MoviesDatabase {
        return MoviesDatabase.getInstance(context)
    }

    fun addMovie(context: Context, listId: Int, movieId: Int) {
        //moviesDatabase = initializeDB(context)

        CoroutineScope(IO).launch {
            val movie = Movies(movie_id = movieId, list_id = listId)
            moviesDatabase!!.moviesDao.insert(movie)
        }
    }

    fun isExist(context: Context, list: Int, movie: Int): LiveData<Boolean> {
        moviesDatabase = initializeDB(context)
        return moviesDatabase!!.moviesDao.isExist(movie, list)
    }

    fun removeMovie(list: Int, movie: Int) {
        CoroutineScope(IO).launch {
            moviesDatabase!!.moviesDao.delete(movie, list)
        }
    }

    fun getMovies(context: Context, list: Int): LiveData<List<Movies>> {
        moviesDatabase = initializeDB(context)
        return moviesDatabase!!.moviesDao.getMovies(list)
    }
}
