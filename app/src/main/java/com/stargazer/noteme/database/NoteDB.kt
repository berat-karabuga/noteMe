package com.stargazer.noteme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database (entities = [NoteEntity::class], version = 1)
//burasıyla beraber ben database in temellerini oturttum ama asıl kodu RoomDatabase yazacak demiş oluyoz
abstract class NoteDB: RoomDatabase(){
    // yine aynu şekilde biz noteDao oluşturduk ama asıl kod NoteDao dan gelcek diyoz e NoteDao ya da kendimiz gidip komutları yazmıştık
    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE: NoteDB? = null

        //context parametresi istememizin sebebi şu context olmadan bu database telefonun hafızasına erişip orada kendine bir yer açamaz
        fun getDatabase(context: Context): NoteDB{
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