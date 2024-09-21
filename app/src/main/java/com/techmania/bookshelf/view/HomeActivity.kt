package com.techmania.bookshelf.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.techmania.bookshelf.R
import com.techmania.bookshelf.apadter.BookPagerAdapter
import com.techmania.bookshelf.databinding.LayoutHomeActivityBinding
import com.techmania.bookshelf.room.DatabaseProvider
import com.techmania.bookshelf.viewmodel.HomeViewModel
import com.techmania.bookshelf.viewmodel.HomeViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private val booksDao by lazy { DatabaseProvider.getDatabase(this).booksDao() }

    private lateinit var binding: LayoutHomeActivityBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var bookPagerAdapter: BookPagerAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.layout_home_activity)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.progress.isVisible = true
        auth = Firebase.auth
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            bookListLiveData.observe(this@HomeActivity) { bookList ->
                val bookMapByYear = bookList.groupBy {
                    val date = Date(it.publishedChapterDate * 1000L)
                    SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
                }.toSortedMap(compareByDescending { it })

                binding.progress.isVisible = false

                bookPagerAdapter = BookPagerAdapter(this@HomeActivity, bookMapByYear)
                binding.viewPager.adapter = bookPagerAdapter


                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    tab.text = bookMapByYear.keys.toList()[position]
                }.attach()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> false
        }
    }

}