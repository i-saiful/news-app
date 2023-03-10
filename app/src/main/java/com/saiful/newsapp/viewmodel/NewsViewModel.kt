package com.saiful.newsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.saiful.newsapp.constant.Category
import com.saiful.newsapp.constant.Constant
import com.saiful.newsapp.database.NewsArticle
import com.saiful.newsapp.database.NewsDatabase
import com.saiful.newsapp.network.NewsApi
import com.saiful.newsapp.repository.NewsRepository
import com.saiful.newsapp.utils.Internet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NewsRepository
    private val result = mutableListOf<NewsArticle>()
    lateinit var readAllNews: LiveData<List<NewsArticle>>

    init {
        val newsDao = NewsDatabase.getDatabase(application).getNewsDao()
        repository = NewsRepository(newsDao)
        readAllNewsFromLocal()
    }

    fun readAllNewsFromLocal() {
        with(repository) {
//            Log.d("TAG", "readAllNewsFromLocal: ${Global.category}")
            readAllNews = when (Constant.category) {
                Category.BUSINESS -> readAllNews(Category.BUSINESS)
                Category.ENTERTAINMENT -> readAllNews(Category.ENTERTAINMENT)
                Category.GENERAL -> readAllNews(Category.GENERAL)
                Category.HEALTH -> readAllNews(Category.HEALTH)
                Category.SCIENCE -> readAllNews(Category.SCIENCE)
                Category.SPORTS -> readAllNews(Category.SPORTS)
                else -> readAllNews(Category.TECHNOLOGY)
            }
        }
    }

    fun loadNewsFromRemote() {
        if (Internet.isOnline()) {
//            Log.d("TAG", "loadNewsFromRemote: call news api")
            viewModelScope.launch {
                try {
                    val response = NewsApi.retrofitService.topHeadlinesNews(
                        Constant.category, Constant.TOKEN
                    )
                    response.articles.map {
                        result.add(
                            NewsArticle(
                                0,
                                it.title,
                                it.author,
                                it.content,
                                it.description,
                                it.publishedAt,
                                it.source?.name,
                                it.url,
                                it.urlToImage,
                                Constant.category
                            )
                        )
                    }
                    addNews()
//                Log.d("TAG", "getTopHeadlines: called ${response.articles.size}")
                } catch (e: Exception) {
                    Log.d("TAG", "$e")
                }
            }
        }
    }

    private fun addNews() {
//        Log.d("TAG", "addNews: result ${result.size}")
        for (i in result) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNews(i)
            }
        }
        result.clear()
//        Log.d("TAG", "addNews: result${result.size}")
    }

    fun addBookmarkNews(newsArticle: NewsArticle) {
//        Log.d("TAG", "addBookmarkNews: ${newsArticle.id}")
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBookmarkNews(
                newsArticle.id, !newsArticle.isBookmark
            )
        }
    }

    fun loadBookmarkNews() {
        readAllNews = repository.readAllBookmarkNews()
    }
}