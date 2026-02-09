package com.stargazer.noteme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesEntity")
data class NoteEntity(
    @PrimaryKey (autoGenerate = true) val id : Int = 0,
    val title: String,
    val content: String,
    val date: Long,
    val imageUrl: String?, //I put a "?" because it should be nunable after all adding a photo to every note isnt mandatory
    val isFavorite: Boolean
)
