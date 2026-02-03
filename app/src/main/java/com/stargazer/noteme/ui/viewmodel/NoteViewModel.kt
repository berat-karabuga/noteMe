package com.stargazer.noteme.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stargazer.noteme.data.local.NoteEntity
import com.stargazer.noteme.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) :ViewModel() {
    //repositorydeki işlemleri viewmodele bağlıyoruz

    val allNotes= repository.allNotes //tüm notları çağırma
    val favoriNotes = repository.getFavorites //favori notları çağırma

    fun insert(note: NoteEntity) = viewModelScope.launch { //not ekleme
        repository.insert(note)
    }


    //parametre olarak aldığımız note bir noteEnity olacak sonra o aldığımız not için repositorydeki delete işlemini çağırıcaz parametre olarak da aldığımız note u ekliyoruz
    fun delete(note: NoteEntity) = viewModelScope.launch { //not silme
        repository.delete(note)
    }


    fun insertNoteWithImage(title: String, content: String, imageUri: Uri?, isFavorite: Boolean){
        viewModelScope.launch {
            val uploadedUrl = imageUri?.let { repository.uploadImage(it) }

            val newNote = NoteEntity(
                title = title,
                content = content,
                date = System.currentTimeMillis(),
                imageUrl = uploadedUrl, // Firebase'den gelen link buraya (Room)
                isFavorite = isFavorite
            )
            repository.insert(newNote)
        }
    }

}