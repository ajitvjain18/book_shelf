package com.techmania.bookshelf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techmania.bookshelf.model.Books
import com.techmania.bookshelf.retrofit.ApiServiceEngine
import com.techmania.bookshelf.retrofit.BookShelfApi
import com.techmania.bookshelf.room.BooksDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val apiService = ApiServiceEngine.buildService(BookShelfApi::class.java)

    private val _bookListLiveData: MutableLiveData<List<Books>> = MutableLiveData()
    val bookListLiveData: LiveData<List<Books>> = _bookListLiveData

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        apiService.fetchBookList().enqueue(object : Callback<List<Books>> {
            override fun onResponse(call: Call<List<Books>>, response: Response<List<Books>>) {
                val booksData = response.body()
                _bookListLiveData.value = booksData
            }

            override fun onFailure(call: Call<List<Books>>, response: Throwable) {
            }

        })
    }

    fun addToDatabase(book: Books) {

    }
}

class HomeViewModelFactory(private val booksDao: BooksDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}