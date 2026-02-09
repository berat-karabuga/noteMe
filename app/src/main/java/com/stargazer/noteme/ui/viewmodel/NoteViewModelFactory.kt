package com.stargazer.noteme.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stargazer.noteme.data.repository.NoteRepository

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    //When the Android system wants to generate a viewModel, it will use this code instead of its own familiar system.


    /* Now here we are using generic types for example <T: ViewModel> this means I will work with a type T
     but it must be a viewmodel or its child modelClass: Class<T> takes the class information of the viewmodel to be created and
      returns :T that is I will take whichever viewmodel type I want because I might want to create different
      viewmodels and use them with this factory*/
    override fun <T : ViewModel> create(modelClass: Class<T>): T {


        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            //We tell the system I checked theress no problem you be quiet otherwise it will give an error because it doesnt find it safe
            @Suppress("UNCHECKED_CAST")
            //We return T to the NoteViewModel object because we initially wanted it to return T
            return NoteViewModel(repository) as T
        }

        throw IllegalArgumentException("Bilinmeyen ViewModel Sınıfı")
    }
}