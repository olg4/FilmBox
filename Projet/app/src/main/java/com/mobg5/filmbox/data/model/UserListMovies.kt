package com.mobg5.filmbox.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/*
* Represents a list of movies (a category)
 */
@Parcelize
@Entity(tableName = "list_movies_table")
data class UserListMovies (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_id")
    var user: String,
    @ColumnInfo(name = "list_title")
    var title: String
): Parcelable
