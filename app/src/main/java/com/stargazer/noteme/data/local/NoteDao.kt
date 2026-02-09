    package com.stargazer.noteme.data.local

    import androidx.room.Dao
    import androidx.room.Delete
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import kotlinx.coroutines.flow.Flow

    @Dao
    interface NoteDao {

        @Insert (onConflict = OnConflictStrategy.REPLACE) //As the name suggests it inserts data and also explains what to do if a note is inserted with the same ıd if itss a job it will use `raplace` meaning it will overwrite the old note with a new one
        suspend fun insertNote (note: NoteEntity)

        @Delete //required to delete the note
        suspend fun deleteNote (note: NoteEntity)

        // Used to retrieve all notes from the table (select * from notesentity) also sorts the retrieved notes from the most recent to the oldest date (order by date DESC)
        @Query ("SELECT * FROM notesEntity ORDER BY date DESC")
        fun getAllNotes(): Flow<List<NoteEntity>>

        //To make a selection insert the items into the table and bring in those that are marked 1 in the 'isFavorite' section i.e. those included in 'isfavorite' its a kind of filtering
        @Query("SELECT * FROM notesEntity WHERE isFavorite = 1")
        fun getAllFavorites(): Flow<List<NoteEntity>>

        @Query("SELECT * FROM notesEntity WHERE id= :noteId")
        fun getSingelNote(noteId: Int): Flow<NoteEntity?>
    }