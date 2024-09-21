package com.techmania.bookshelf.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techmania.bookshelf.model.Books

@Dao
interface BooksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Books)

    @Query("SELECT * FROM books")
    fun getAllBooks(): List<Books>
}
