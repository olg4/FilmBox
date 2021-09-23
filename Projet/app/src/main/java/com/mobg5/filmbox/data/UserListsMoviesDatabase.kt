package com.mobg5.filmbox.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobg5.filmbox.data.model.UserListMovies

@Database(entities = [UserListMovies::class], version = 1, exportSchema = false)
abstract class UserListsMoviesDatabase: RoomDatabase() {

    abstract val listsMoviesDao: UserListsMoviesDao

    companion object {
        @Volatile
        private var INSTANCE: UserListsMoviesDatabase? = null

        fun getInstance(context: Context): UserListsMoviesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserListsMoviesDatabase::class.java,
                        "list_movies_database").fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}