package dead.code.note.viewmodel.providerFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dead.code.note.repositories.NotesRepo
import dead.code.note.viewmodel.NotesViewModel

class NotesViewModelProviderFactory(
    private val notesRepo: NotesRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepo) as T
    }
}

