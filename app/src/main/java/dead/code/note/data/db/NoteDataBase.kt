package dead.code.note.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dead.code.note.data.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDataBase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        @Volatile
        var INSTANCE: NoteDataBase? = null

        @Synchronized
        fun getDataBaseInstance(context: Context): NoteDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, NoteDataBase::class.java,
                    "notes.db",
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}