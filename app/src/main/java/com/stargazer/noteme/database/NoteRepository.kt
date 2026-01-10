package com.stargazer.noteme.database

import android.net.Uri
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao){
    val allNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes() //tüm notları alma komutu
    val getFavorites: Flow<List<NoteEntity>> = noteDao.getAllFavorites() //tüm favorileri alma komutu

    suspend fun insert(note: NoteEntity){ //note ekleme komutu
        noteDao.insertNote(note)
    }
    suspend fun delete(note: NoteEntity){ //note silme komutu
        noteDao.deleteNote(note)
    }

    //firebase storege a ekleyeceğimiz görseller şu anlık içi boş daha sonra doldurulacak
    suspend fun uploadImage(imageUri: Uri): String{
        return ""
    }
}