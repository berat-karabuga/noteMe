package com.stargazer.noteme.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database (entities = [NoteEntity::class], version = 1)
//This means Ive laid the foundation for the database but RoomDatabase will write the actual code
abstract class NoteDB: RoomDatabase(){
    // Similarly we created a NoteDao code but were saying the actual code will come from NoteDao we either went to NoteDao or wrote the commands ourselves
    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE: NoteDB? = null

        //The reason we require the context parameter is that without context this database cannot access the phones memory and allocate space for itself there
        fun getDatabase(context: Context): NoteDB {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}