import androidx.room.Database
import androidx.room.RoomDatabase
import com.techmania.bookshelf.model.Books
import com.techmania.bookshelf.room.BooksDao

@Database(entities = [Books::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
}

