package dead.code.note.adaptor

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dead.code.note.R
import dead.code.note.data.Note
import dead.code.note.databinding.NotesItemBinding
import kotlin.random.Random

class NotesAdaptor : RecyclerView.Adapter<NotesAdaptor.NotesViewHolder>() {

    inner class NotesViewHolder(private val binding: NotesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val myCard = binding.cardItem
        fun bind(note: Note) {
            binding.tvNoteTitle.text = note.noteTitle

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = differ.currentList.size

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
       // holder.myCard.setCardBackgroundColor(holder.itemView.resources.getColor(getRandomColor(), null))

        val note = differ.currentList[position]

        holder.bind(note)
        holder.itemView.setOnClickListener {
            onClick?.invoke(note)
        }
    }


    var onClick: ((Note) -> Unit?)? = null

    fun getRandomColor(): Int {
        val backGroundCard = arrayListOf<Int>()
        backGroundCard.add(R.color.teal_200)
        backGroundCard.add(R.color.purple_200)
        backGroundCard.add(R.color.white)
        backGroundCard.add(R.color.purple_700)
        val random = Random
        val number = random.nextInt(backGroundCard.size)
        return number
    }
}