package com.hunnybunny.note_room.db

import androidx.room.*


@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note:Note)

    //suspend means we cant call this function directly it means that we need and coroutine scope for calling it.

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNotes():List<Note>

    @Insert
    suspend fun addMultipleNote(vararg  note: Note)

    @Update
    suspend fun updateNote(note :Note)

    @Delete
    suspend fun deleteNote(note: Note)
}