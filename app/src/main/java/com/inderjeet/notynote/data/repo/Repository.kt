package com.inderjeet.notynote.data.repo

import android.content.Context
import com.inderjeet.notynote.data.room.NoteDataBase
import com.inderjeet.notynote.data.models.Note
import kotlinx.coroutines.flow.Flow

class Repository(context: Context) {

    private val notesDao= NoteDataBase.getNotesDatabase(context)?.NoteDao()

    fun getNotes():Flow<List<Note>>{
        return notesDao!!.getAllNote()
    }
    suspend fun insertNote(note: Note){
        notesDao?.saveNote(note)
    }

    suspend fun deleteNote(id:Int){
        notesDao?.deleteNote(id)
    }

}