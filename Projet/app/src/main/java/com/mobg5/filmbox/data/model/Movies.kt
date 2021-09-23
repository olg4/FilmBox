package com.mobg5.filmbox.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/*
 the movie id is taking the same list's id
 */
@Parcelize
@Entity(tableName = "movies_table")
data class Movies (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movie_id: Int,
    val list_id: Int,
): Parcelable