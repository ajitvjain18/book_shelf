package com.techmania.bookshelf.apadter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.techmania.bookshelf.model.Books
import com.techmania.bookshelf.view.BookFragment

class BookPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val bookList: Map<String, List<Books>>
) :
    FragmentStateAdapter(fragmentActivity) {

    private val dates = bookList.keys.toList()

    override fun getItemCount(): Int = bookList.size

    override fun createFragment(position: Int): Fragment {
        val date = dates[position]
        return BookFragment.newInstance(bookList[date] ?: emptyList())
    }
}