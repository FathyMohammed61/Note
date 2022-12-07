package dead.code.note.repositories

import dead.code.note.data.Note
import dead.code.note.data.db.NoteDataBase


class NotesRepo(noteDataBase: NoteDataBase) {

    private val noteDao = noteDataBase.noteDao

    suspend fun upsertNote(note: Note) = noteDao.upsertNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    suspend fun deleteAllNotes() = noteDao.deleteAllNotes()

    fun getNotes() = noteDao.selectNotes()

    fun searchNotes(searchQuery: String) = noteDao.searchInNotesTitle(searchQuery)

}