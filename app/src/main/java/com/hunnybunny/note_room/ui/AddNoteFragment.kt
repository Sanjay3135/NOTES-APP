package com.hunnybunny.note_room.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.hunnybunny.note_room.R
import com.hunnybunny.note_room.db.Note
import com.hunnybunny.note_room.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

 private var note:Note?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        NoteDatabase(requireActivity()).getNoteDao()


        arguments?.let {
            note=AddNoteFragmentArgs.fromBundle(it).note
            edit_text_title.setText(note?.title)
            edit_text_note.setText(note?.note)
        }

        button_save.setOnClickListener {view->
            val noteTitle=edit_text_title.text.toString().trim()
            val noteBody= edit_text_note.text.toString().trim()
            if(noteTitle.isEmpty())
            {
                edit_text_title.error="Title Required"
                edit_text_title.requestFocus()
                return@setOnClickListener
            }
            if(noteBody.isEmpty())
            {
                edit_text_note.error="Note required"
                edit_text_note.requestFocus()
                return@setOnClickListener
            }

            launch {
            context?.let {
                // checking if context is not null
                val mnote=Note(noteTitle,noteBody)
                    if(note==null)
                    {
                        NoteDatabase(it).getNoteDao().addNote(mnote)
                        it.toast("Note Saved Hurray")
                    }
                else{
                        mnote.id= note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mnote)
                        it.toast("Note updated")
                    }
                val action=AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                Navigation.findNavController(view).navigate(action)
//                fini
              }
            }


        }
    }
        private fun deleteNote()
        {
            context?.let {
                AlertDialog.Builder(it).apply {
                    setTitle("Are you Sure ")
                    setMessage("You Can't Undo this Operation")
                    setPositiveButton("Yes"){_,_ ->
                        launch {
                            NoteDatabase(context).getNoteDao().deleteNote(note!!)
                            val action=AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                            Navigation.findNavController(requireView()).navigate(action)
                        }
                    }
                    setNegativeButton("No"){_,_ ->
                    }

                }.create().show()
            }
        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.action_settings->context?.toast("In Development")

            R.id.delete -> {
            if(note!=null)deleteNote() else context?.toast("Cannot Delete Item")
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}