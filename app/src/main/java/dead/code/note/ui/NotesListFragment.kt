package dead.code.note.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dead.code.note.R
import dead.code.note.adaptor.NotesAdaptor
import dead.code.note.adaptor.VerticalItemDecoration
import dead.code.note.databinding.FragmentNotesListBinding
import dead.code.note.viewmodel.NotesViewModel

class NotesListFragment : Fragment() {
    private lateinit var binding: FragmentNotesListBinding
    private lateinit var notesAdaptor: NotesAdaptor
    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        lifecycleScope.launchWhenCreated {
            viewModel.notes.collect { notesList ->
                notesAdaptor.differ.submitList(notesList)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchNotes.collect { searchNotes ->
                notesAdaptor.differ.submitList(searchNotes)
            }
        }

        binding.edSearch.addTextChangedListener {
            viewModel.searchNotes(it.toString().trim())
        }

        binding.btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_noteFragment)
        }
        notesAdaptor.onClick = { note ->
            val bundle = Bundle().apply {
                putParcelable("note", note)
            }
            findNavController().navigate(R.id.action_notesListFragment_to_noteFragment, bundle)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setUpRecyclerView() {
        notesAdaptor = NotesAdaptor()
        binding.apply {
            rvNotes.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = notesAdaptor
                addItemDecoration(VerticalItemDecoration(height = 30))
            }
        }
    }
}