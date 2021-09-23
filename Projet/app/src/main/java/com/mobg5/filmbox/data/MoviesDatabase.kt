package com.mobg5.filmbox.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobg5.filmbox.data.model.Movies

@Database(entities = [Movies::class], version = 1, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {

    abstract val moviesDao: MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java,
                        "movies_database").fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}