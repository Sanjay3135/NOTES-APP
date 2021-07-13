package com.hunnybunny.note_room.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hunnybunny.note_room.R
import com.hunnybunny.note_room.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.note_layout.*
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_home, container, false)
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        text_view_note.isVisible=false
        recycler_view_notes.setHasFixedSize(true)
        recycler_view_notes.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//        recycler_view_notes.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes=NoteDatabase(it).getNoteDao().getAllNotes()
               recycler_view_notes.adapter=NotesAdapter(notes)
            }
        }


     button_add.setOnClickListener {
         val action=HomeFragmentDirections.actionAddNote()
         Navigation.findNavController(it).navigate(action)
     }
    }
}
