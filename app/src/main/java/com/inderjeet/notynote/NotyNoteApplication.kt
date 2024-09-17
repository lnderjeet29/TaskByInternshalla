package com.inderjeet.notynote

import android.app.Application
import com.google.firebase.FirebaseApp

class NotyNoteApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}