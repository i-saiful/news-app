package com.saiful.newsapp.repository

import com.saiful.newsapp.database.NewsArticle
import com.saiful.newsapp.database.NewsDao

class NewsRepository(private val newsDao: NewsDao) {

    fun readAllNews(category: String) = newsDao.readAllNews(category)

    suspend fun addNews(newsArticle: NewsArticle) {
        newsDao.addNews(newsArticle)
    }

    suspend fun addBookmarkNews(id: Int, isBookmark: Boolean) {
        newsDao.addBookmarkNews(id, isBookmark)
    }

    fun readAllBookmarkNews() = newsDao.readAllBookmarkNews()

}