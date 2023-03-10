package com.saiful.newsapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.saiful.newsapp.constant.Category
import com.saiful.newsapp.constant.Constant
import com.saiful.newsapp.constant.Constant.Companion.TOKEN
import com.saiful.newsapp.database.NewsArticle
import com.saiful.newsapp.database.NewsDatabase
import com.saiful.newsapp.network.NewsApi
import com.saiful.newsapp.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsApiCallWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
//        Log.d("TAG", "readFromNews: work manager doWork")
        try {
            val scope = CoroutineScope(Dispatchers.IO)
//        Business
            scope.launch {
                val response = NewsApi.retrofitService.topHeadlinesBusinessNews(
                    Category.BUSINESS, TOKEN
                )
                for (i in response.articles) {
                    val newsDao = NewsDatabase.getDatabase(Constant.context!!).getNewsDao()
                    val repository = NewsRepository(newsDao)
                    repository.addNews(
                        NewsArticle(
                            0,
                            i.title,
                            i.author,
                            i.content,
                            i.description,
                            i.publishedAt,
                            i.source?.name,
                            i.url,
                            i.urlToImage,
                            Category.BUSINESS,
                            false
                        )
                    )
                }
            }

            //        entertainment
            scope.launch {
                val response = NewsApi.retrofitService.topHeadlinesBusinessNews(
                    Category.ENTERTAINMENT, TOKEN
                )
                for (i in response.articles) {
                    val newsDao = NewsDatabase.getDatabase(Constant.context!!).getNewsDao()
                    val repository = NewsRepository(newsDao)
                    repository.addNews(
                        NewsArticle(
                            0,
                            i.title,
                            i.author,
                            i.content,
                            i.description,
                            i.publishedAt,
                            i.source?.name,
                            i.url,
                            i.urlToImage,
                            Category.ENTERTAINMENT,
                            false
                        )
                    )
                }
            }

            //        general
            scope.launch {
                val response = NewsApi.retrofitService.topHeadlinesBusinessNews(
                    Category.GENERAL, TOKEN
                )
                for (i in response.articles) {
                    val newsDao = NewsDatabase.getDatabase(Constant.context!!).getNewsDao()
                    val repository = NewsRepository(newsDao)
                    repository.addNews(
                        NewsArticle(
                            0,
                            i.title,
                            i.author,
                            i.content,
                            i.description,
                            i.publishedAt,
                            i.source?.name,
                            i.url,
                            i.urlToImage,
                            Category.GENERAL,
                            false
                        )
                    )
                }
            }

            //        health
            scope.launch {
                val response = NewsApi.retrofitService.topHeadlinesBusinessNews(
                    Category.HEALTH, TOKEN
                )
                for (i in response.articles) {
                    val newsDao = NewsDatabase.getDatabase(Constant.context!!).getNewsDao()
                    val repository = NewsRepository(newsDao)
                    repository.addNews(
                        NewsArticle(
                            0,
                            i.title,
                            i.author,
                            i.content,
                            i.description,
                            i.publishedAt,
                            i.source?.name,
                            i.url,
                            i.urlToImage,
                            Category.HEALTH,
                            false
                        )
                    )
                }
            }

            //        science
            scope.launch {
                val response = NewsApi.retrofitService.topHeadlinesBusinessNews(
                    Category.SCIENCE, TOKEN
                )
                for (i in response.articles) {
                    val newsDao = NewsDatabase.getDatabase(Constant.context!!).getNewsDao()
                    val repository = NewsRepository(newsDao)
                    repository.addNews(
                        NewsArticle(
                            0,
                            i.title,
                            i.author,
                            i.content,
                            i.description,
                            i.publishedAt,
                            i.source?.name,
                            i.url,
                            i.urlToImage,
                            Category.SCIENCE,
                            false
                        )
                    )
                }
            }

            //        sports
            scope.launch {
                val response = NewsApi.retrofitService.topHeadlinesBusinessNews(
                    Category.SPORTS, TOKEN
                )
                for (i in response.articles) {
                    val newsDao = NewsDatabase.getDatabase(Constant.context!!).getNewsDao()
                    val repository = NewsRepository(newsDao)
                    repository.addNews(
                        NewsArticle(
                            0,
                            i.title,
                            i.author,
                            i.content,
                            i.description,
                            i.publishedAt,
                            i.source?.name,
                            i.url,
                            i.urlToImage,
                            Category.SPORTS,
                            false
                        )
                    )
                }
            }

            //        technology
            scope.launch {
                val response = NewsApi.retrofitService.topHeadlinesBusinessNews(
                    Category.TECHNOLOGY, TOKEN
                )
                for (i in response.articles) {
                    val newsDao = NewsDatabase.getDatabase(Constant.context!!).getNewsDao()
                    val repository = NewsRepository(newsDao)
                    repository.addNews(
                        NewsArticle(
                            0,
                            i.title,
                            i.author,
                            i.content,
                            i.description,
                            i.publishedAt,
                            i.source?.name,
                            i.url,
                            i.urlToImage,
                            Category.TECHNOLOGY,
                            false
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.d("TAG", "$e")
        }
        return Result.success()
    }
}