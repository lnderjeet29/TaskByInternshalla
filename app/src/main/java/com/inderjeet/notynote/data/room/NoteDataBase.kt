package com.inderjeet.notynote.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inderjeet.notynote.data.models.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDataBase: RoomDatabase() {
    abstract fun NoteDao(): NoteDao

    companion object {
        private var INSTANCES: NoteDataBase? = null

        fun getNotesDatabase(ApplicationContext: Context): NoteDataBase? {
            if (INSTANCES == null) {
                INSTANCES = Room.databaseBuilder(ApplicationContext, NoteDataBase::class.java , "noteDB").build()

            }
            return INSTANCES
        }
    }
}