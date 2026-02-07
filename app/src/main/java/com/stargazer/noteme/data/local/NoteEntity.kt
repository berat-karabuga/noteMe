package com.stargazer.noteme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "notesEntity")
    data class NoteEntity(
        @PrimaryKey (autoGenerate = true) val id : Int = 0,
        val title: String,
        val content: String,
        val date: Long,
        val imageUrl: String?, // "?" koydum çünkü nunable olabilmeli sonuçta her nota fotoğraf eklenmesi zorunlu değil
        val isFavorite: Boolean
    )
