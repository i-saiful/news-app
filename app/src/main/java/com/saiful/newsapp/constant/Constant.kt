package com.saiful.newsapp.constant

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.saiful.newsapp.database.NewsArticle

class Constant {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
//        const val TOKEN = "b9aec1e5c45b4c27b89627110fe05e45"
        const val TOKEN = "9d68e0d1f113454cb2e1e6ad604ac096"

        var category: String = Category.BUSINESS
        var newsArticle: NewsArticle? = null

        @SuppressLint("StaticFieldLeak") var contextView: View? = null
        @SuppressLint("StaticFieldLeak") var context: Context? = null
    }
}