package com.stargazer.noteme.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.storage.FirebaseStorage
import com.stargazer.noteme.data.local.NoteEntity
import com.stargazer.noteme.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NoteViewModel(private val repository: NoteRepository) :ViewModel() {
    // We link the operations in the repository to the viewmodel

    val allNotes= repository.allNotes //calling all notes
    val favoriNotes = repository.getFavorites
    var selectedNoteForDelete by mutableStateOf<NoteEntity?>(null)
    val favoriteNotes = repository.getFavorites.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    var selectedCategoryByHome by mutableStateOf<String?>(null)

    fun insert(note: NoteEntity) = viewModelScope.launch { //add note
        repository.insert(note)
    }


    /*The note we receive as a parameter will be a noteEnity then we will call the delete operation in the repository
    for that note and we add the note we received as a parameter*/
    fun getsingelNote(id: Int):Flow<NoteEntity?>{
        return repository.getSingleNote(id)
    }

    fun updateNote(note: NoteEntity) = viewModelScope.launch {
        repository.insert(note) //Since there is an OnConflictStrategy.REPLACE in the room it updates if the insert comes with the same ıd
    }



    fun deleteFullNote(onComplete: () -> Unit) = viewModelScope.launch {
        selectedNoteForDelete?.let { note ->
            note.imageUrl?.let { url ->
                try {
                    FirebaseStorage.getInstance().getReferenceFromUrl(url).delete().await()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            repository.deleteNote(note)
            selectedNoteForDelete = null
            onComplete()
        }
    }



    fun insertNoteWithImage(title: String, content: String, imageUri: Uri?, isFavorite: Boolean){
        viewModelScope.launch {
            val uploadedUrl = imageUri?.let { repository.uploadImage(it) }

            val newNote = NoteEntity(
                title = title,
                content = content,
                date = System.currentTimeMillis(),
                imageUrl = uploadedUrl, //The link from Firebase goes here
                isFavorite = isFavorite
            )
            repository.insert(newNote)
        }
    }

}