package dead.code.note.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dead.code.note.data.db.NoteDataBase
import dead.code.note.databinding.ActivityMainBinding
import dead.code.note.repositories.NotesRepo
import dead.code.note.viewmodel.NotesViewModel
import dead.code.note.viewmodel.providerFactory.NotesViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    val viewModel: NotesViewModel by lazy {
        val dataBase = NoteDataBase.getDataBaseInstance(this@MainActivity)
        val notesRepo = NotesRepo(dataBase)
        val notesProviderFactory = NotesViewModelProviderFactory(notesRepo)
        ViewModelProvider(this, notesProviderFactory)[NotesViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}