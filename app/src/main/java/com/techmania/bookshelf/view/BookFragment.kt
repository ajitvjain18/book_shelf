package com.techmania.bookshelf.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.techmania.bookshelf.apadter.BookAdapter
import com.techmania.bookshelf.databinding.LayoutBookFragmentBinding
import com.techmania.bookshelf.model.Books
import com.techmania.bookshelf.viewmodel.HomeViewModel

class BookFragment : Fragment() {

    private lateinit var binding: LayoutBookFragmentBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutBookFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookList: List<Books>? = arguments?.getParcelableArrayList(ARG_BOOK_LIST)
        binding.rv.adapter = bookList?.let { BookAdapter(it) }
    }


    companion object {
        private const val ARG_BOOK_LIST = "book_list"

        fun newInstance(bookList: List<Books>): BookFragment {
            val fragment = BookFragment()
            val args = Bundle().apply {
                putParcelableArrayList(ARG_BOOK_LIST, ArrayList(bookList))
            }
            fragment.arguments = args
            return fragment
        }
    }
}