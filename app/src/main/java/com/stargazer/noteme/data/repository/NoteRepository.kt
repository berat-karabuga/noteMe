package com.stargazer.noteme.data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.stargazer.noteme.data.local.NoteDao
import com.stargazer.noteme.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class NoteRepository(private val noteDao: NoteDao){
    private val storageInstance = FirebaseStorage.getInstance().reference
    val allNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes() //tüm notları alma komutu
    val getFavorites: Flow<List<NoteEntity>> = noteDao.getAllFavorites() //tüm favorileri alma komutu

    suspend fun insert(note: NoteEntity){ //note ekleme komutu
        noteDao.insertNote(note)
    }
    suspend fun delete(note: NoteEntity){ //note silme komutu
        noteDao.deleteNote(note)
    }

    suspend fun uploadImage(imageUri: Uri): String{
        return try {
            //UUID sayesinde her görsel için özel bir isim oluşturucaz
            val fileName = "images/${UUID.randomUUID()}.jpg"
            val imageRef = storageInstance.child(fileName)

            //not yüklenene kadar beklenmesini istiyorum
            imageRef.putFile(imageUri).await()

            //yüklenen görselin linkini alıyorum
            val downloadUrl = imageRef.downloadUrl.await()
            downloadUrl.toString()

        } catch (e: Exception){
            e.printStackTrace()
            ""
        }
    }
}