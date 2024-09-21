package com.techmania.bookshelf.retrofit

import com.techmania.bookshelf.model.Books
import com.techmania.bookshelf.model.Country
import retrofit2.Call
import retrofit2.http.GET

interface BookShelfApi {

    @GET("IU1K")
    fun fetchCountryList(): Call<List<Country>>

    @GET("CNGI")
    fun fetchBookList() : Call<List<Books>>

}