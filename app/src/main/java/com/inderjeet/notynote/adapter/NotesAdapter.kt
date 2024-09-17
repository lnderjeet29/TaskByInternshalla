package com.inderjeet.notynote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inderjeet.notynote.R
import com.inderjeet.notynote.data.models.Note
import com.inderjeet.notynote.databinding.ItemNoteBinding

class NotesAdapter(
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Note) -> Unit,
    private val onView: (Note) -> Unit
) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(NoteDiffCallback) {

    // Define the DiffUtil callback as an object to compare items efficiently
    object NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem == newItem
    }

    // ViewHolder class to handle the note view
    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            with(binding) {
                tvtitle.text = note.title
                tvDes.text = note.description

                root.setOnClickListener { showPopupMenu(note) }
            }
        }

        // Show a popup menu when the root view is clicked
        private fun showPopupMenu(note: Note) {
            val popupMenu = PopupMenu(binding.root.context, binding.root).apply {
                setForceShowIcon(true)
                inflate(R.menu.menu_option)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> onDelete(note.id)
                        R.id.Edit_txt -> onEdit(note)
                        R.id.show -> onView(note)
                    }
                    true
                }
            }
            popupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }
}