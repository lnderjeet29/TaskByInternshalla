package com.inderjeet.notynote.ui.fragments.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.inderjeet.notynote.adapter.NotesAdapter
import com.inderjeet.notynote.databinding.FragmentNotesBinding
import com.inderjeet.notynote.ui.fragments.addupdate.AddNoteFragment
import com.inderjeet.notynote.ui.viewmodels.NotesViewModel
import com.inderjeet.notynote.ui.viewmodels.NotesViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesFragment : Fragment() {

    private val binding by lazy {
        FragmentNotesBinding.inflate(layoutInflater)
    }

    private val notesViewModel by lazy {
        ViewModelProvider(
            this,
            NotesViewModelFactory(requireContext())
        )[NotesViewModel::class.java]
    }

    private val noteNotesAdapter by lazy {
        NotesAdapter(
            {
                notesViewModel.deleteNote(it)
            }, {
                AddNoteFragment(it).show(childFragmentManager, "EditNote")
            }, {
                AddNoteFragment(it, true).show(childFragmentManager, "showNote")
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setUpAdapter()
        onClick()
        setUpAdapter()
        observer()
        return binding.root
    }

    private fun observer() {
        lifecycleScope.launch {
            notesViewModel.getNotes().collectLatest {
                withContext(Dispatchers.Main) {
                    noteNotesAdapter.submitList(it)
                }
            }
        }
    }

    private fun setUpAdapter() {
        binding.apply {
            rvNotes.adapter = noteNotesAdapter
        }
    }

    private fun onClick() {
        binding.apply {
            favAddNote.setOnClickListener {
                AddNoteFragment().show(childFragmentManager, "AddNote")
            }
        }
    }

}