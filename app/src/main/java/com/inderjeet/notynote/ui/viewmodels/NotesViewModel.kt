package com.inderjeet.notynote.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.inderjeet.notynote.data.models.Note

import com.inderjeet.notynote.data.repo.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class NotesViewModel:ViewModel() {
    private lateinit var repo: Repository

    fun initDB(applicationContext: Context){
        repo= Repository(applicationContext)
    }

    fun getNotes(): Flow<List<Note>> {
        return repo.getNotes()
    }

    fun insertNotes(note: Note){
        viewModelScope.launch {
            repo.insertNote(note)
        }
    }

    fun deleteNote(id:Int){
       viewModelScope.launch {
           repo.deleteNote(id)
       }
    }
}

class NotesViewModelFactory(private val applicationContext: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            val viewModel = NotesViewModel()
            viewModel.initDB(applicationContext) // Initialize the ViewModel with the context
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}