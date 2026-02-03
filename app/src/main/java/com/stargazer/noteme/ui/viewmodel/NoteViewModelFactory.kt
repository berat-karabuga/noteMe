package com.stargazer.noteme.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stargazer.noteme.data.repository.NoteRepository

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    //android sistemi viewModel üretmek istediğinde bu kodu kullanacak kendi bildiği sistem yerine

    /* şimdi burada<T> yani jenerik türleri kullanıyoruz örneğin mesela <T: ViewModel> burada şunu demiş oluyoruz ben bir
    * T türüyle çalışıcam ama bu mutlaka bir viewmodel ya da onun çocuğu olmalı
    * modelClass: Class<T> oluşturulacak viewmodelin sınıf bilgisini alıyoruz ve :T döndürüyoruz yani
    * hangi viewmodel türünü istediysem onu alıcam çünkü farklı viewmodeller oluşturup onları da bu factory ile kullanmak isteyebilirim*/
    override fun <T : ViewModel> create(modelClass: Class<T>): T {


        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            //sisteme diyoruz ki abi ben kontrol ettim sıkıntı yok sen sus yokssa o güvenli bulmayıp hata veriyor
            @Suppress("UNCHECKED_CAST")
            //NoteViewModel objesini T döndürüyoruz çünkü T dönmesini istemiştik başta
            return NoteViewModel(repository) as T
        }

        throw IllegalArgumentException("Bilinmeyen ViewModel Sınıfı")
    }
}