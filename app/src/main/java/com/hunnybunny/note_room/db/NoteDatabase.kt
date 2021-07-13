package com.hunnybunny.note_room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
        entities = [Note::class],
      version=1
)
abstract class NoteDatabase :RoomDatabase(){

    abstract  fun getNoteDao():NoteDao


    companion object{
        @Volatile private var instance:NoteDatabase?=null
        private val LOCK=Any()
        operator fun invoke(context: Context)= instance?: synchronized(LOCK){///if instance is not null than we return as it is but is if it is null
           // invoke function automatically gotinvoked when we NoteDatabase              ///than synchronized block will make assign irt
            instance?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context)=Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
        "notedatabase"
        ).build()
    }
}