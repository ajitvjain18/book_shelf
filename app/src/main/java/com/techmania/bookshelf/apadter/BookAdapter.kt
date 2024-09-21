package com.techmania.bookshelf.apadter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techmania.bookshelf.databinding.LayoutBookRvItemsBinding
import com.techmania.bookshelf.model.Books

class BookAdapter(private val bookItemList: List<Books>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutBookRvItemsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) {
        holder.bind(bookItemList[position])
    }

    override fun getItemCount() = bookItemList.size


    inner class ViewHolder(private val binding: LayoutBookRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Books) {
            with(binding) {
                title.text = book.title
                score.text = book.score.toString()
                popularity.text = book.popularity.toString()
                Glide.with(root.context).load(book.image).into(image)
            }
        }
    }
}