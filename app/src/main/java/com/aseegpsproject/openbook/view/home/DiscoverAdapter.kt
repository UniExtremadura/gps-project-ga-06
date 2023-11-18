package com.aseegpsproject.openbook.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aseegpsproject.openbook.databinding.DiscoverItemListBinding
import com.aseegpsproject.openbook.model.Book

class DiscoverAdapter(
    private val books: List<Book>,
    private val onClick: (book: Book) -> Unit,
    private val onLongClick: (title: Book) -> Unit
) : RecyclerView.Adapter<DiscoverAdapter.BookViewHolder>() {

    class BookViewHolder(
        private val binding: DiscoverItemListBinding,
        private val onClick: (book: Book) -> Unit,
        private val onLongClick: (title: Book) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            with(binding) {
                bookTitle.text = book.title
                bookAuthor.text = book.author
                bookYear.text = book.year
                bookCover.setImageResource(book.coverImage)
                clItem.setOnClickListener {
                    onClick(book)
                }
                clItem.setOnLongClickListener {
                    onLongClick(book)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            DiscoverItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, onClick, onLongClick)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

}