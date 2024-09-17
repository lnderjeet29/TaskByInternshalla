package com.inderjeet.notynote.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inderjeet.notynote.data.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("select * from notes")
    fun getAllNote(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: Note)

    @Query("Delete from Notes Where id is :id")
    suspend fun deleteNote(id: Int)

}