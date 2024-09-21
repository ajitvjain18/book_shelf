package com.techmania.bookshelf.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techmania.bookshelf.model.Books
import com.techmania.bookshelf.model.Country
import com.techmania.bookshelf.retrofit.ApiServiceEngine
import com.techmania.bookshelf.retrofit.BookShelfApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {


    private val apiService = ApiServiceEngine.buildService(BookShelfApi::class.java)

    private val _countryListLiveData: MutableLiveData<List<Country>> = MutableLiveData()
    val countryListLiveData: LiveData<List<Country>> = _countryListLiveData

    init {
        fetchBook()
    }

    private fun fetchBook() {
        apiService.fetchBookList().enqueue(object : Callback<List<Books>> {
            override fun onResponse(call: Call<List<Books>>, response: Response<List<Books>>) {
                val booksData = response.body()
            }

            override fun onFailure(call: Call<List<Books>>, response: Throwable) {
            }

        })
    }

    fun fetchCountry() {
        apiService.fetchCountryList().enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {
                    val studentsList = response.body()
                    _countryListLiveData.value = studentsList
                }
            }

            override fun onFailure(call: Call<List<Country>>, response: Throwable) {
                Log.d("ajit", "onFailure: " + response.message)
            }

        })
    }

}