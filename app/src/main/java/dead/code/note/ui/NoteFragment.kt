package dead.code.note.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dead.code.note.R
import dead.code.note.data.Note
import dead.code.note.databinding.FragmentNoteBinding
import dead.code.note.viewmodel.NotesViewModel


class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    private val args by navArgs<NoteFragmentArgs>()

    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.note?.let {
            binding.apply {
                edTitle.setText(it.noteTitle)
                edNote.setText(it.noteText)
            }
            binding.imgDeleteNote.visibility = View.VISIBLE
        }

        binding.apply {
            btnSaveNote.setOnClickListener {
                val id = args.note?.noteId ?: 0
                val noteTitle = edTitle.text.toString()
                val noteText = edNote.text.toString()
                Note(id, noteTitle, noteText).also { note ->
                    if (noteTitle.isEmpty() && noteText.isEmpty()) {
                        Toast.makeText(context, "All fields must be filled", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    viewModel.upsertNote(note)
                    findNavController().navigateUp()
                }
            }
        }

        binding.apply {
            imgDeleteNote.setOnClickListener {
                val noteID = args.note!!.noteId
                val noteTitle = edTitle.text.toString()
                val noteText = edNote.text.toString()
                alertDelete(noteID, noteTitle, noteText)
            }
        }


    }

    private fun alertDelete(id: Int, title: String, text: String): AlertDialog {
        val alertDelete = AlertDialog.Builder(requireActivity()).apply {
            setIcon(R.drawable.ic_delete)
            setMessage("Do you want delete it")
            setPositiveButton("yes") { _, _ ->
                Note(id, title, text).also {
                    viewModel.deleteNotes(it)
                    findNavController().navigateUp()
                }
            }
            setNegativeButton("No") { d, _ ->
                d.dismiss()
                findNavController().navigateUp()
            }
            create()
        }
        return alertDelete.show()
    }


}