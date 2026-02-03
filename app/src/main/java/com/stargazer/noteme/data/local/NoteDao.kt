package com.stargazer.noteme.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE) //kelime anlamından da anlaşılacağı üzere veriyi ınsert ediyor ayrıca aynı id ile not ınsert edilirse ne yapılması gerektiğini anlatıyor yapacağı şey işse raplace yani eskinin üzerine yazacak yeni notu
    suspend fun insertNote (note: NoteEntity)

    @Delete //notu silmek için gerekli
    suspend fun deleteNote (note: NoteEntity)

    // tablodaki tüm notoları getirmek için kullanılır (select * from notesentitiy), ayrıca getirdiğin notların en yakın tarihten en eskiye göre soralanmasını sağlar(order by date DESC)
    @Query ("SELECT * FROM notesEntity ORDER BY date DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    //seçim yapmak için tabloya giy ve isFavorite kısımlarında 1 olan yani isfavorit'e dahil olanları getir bir nevi filtreleme
    @Query("SELECT * FROM notesEntity WHERE isFavorite = 1")
    fun getAllFavorites(): Flow<List<NoteEntity>>
}