package com.inderjeet.notynote.ui.fragments.addupdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inderjeet.notynote.ui.viewmodels.NotesViewModel
import com.inderjeet.notynote.data.models.Note
import com.inderjeet.notynote.databinding.BottomFragmentBinding
import java.util.*

class AddNoteFragment(val noteForEdit: Note?=null, val onView:Boolean?=false) : BottomSheetDialogFragment() {

    private val binding by lazy {
        BottomFragmentBinding.inflate(layoutInflater)
    }
    private val notesViewModel by lazy {
        ViewModelProvider(this)[NotesViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstantanceState: Bundle?
    ):View?{
        notesViewModel.initDB(applicationContext = requireContext())
        dialog?.window?.setDimAmount(0f)
        initUi()
        onClick()
        return binding.root
    }

    private fun initUi() {
        if(onView == true){
        noteForEdit?.let {
            binding.apply {
                etTitle.isFocusable=false
                etDesc.isFocusable=false
                btnAdd.visibility=View.GONE
                etTitle.setText(noteForEdit.title)
                etDesc.setText(noteForEdit.description)
                return
            }
        }
    }
        noteForEdit?.let {
            binding.apply {
                etTitle.setText(noteForEdit.title)
                etDesc.setText(noteForEdit.description)
            }
        }
}

    private fun onClick(){
        binding.apply {
            btnAdd.setOnClickListener  {
                if(etTitle.text.toString().trim().isEmpty()){
                    etTitle.error="empty title"
                    return@setOnClickListener
                }
                if(etDesc.text.toString().trim().isEmpty()){
                    etDesc.error="empty something here"
                    return@setOnClickListener
                }
                val note= Note(
                    title = etTitle.text.toString().trim(),
                    description = etDesc.text.toString().trim(),
                    date = Calendar.getInstance()[Calendar.DATE])
                noteForEdit?.let {
                    note.id=it.id
                    note.date=it.date
                }
                notesViewModel.insertNotes(note)
                dismiss()
            }
        }
    }

}