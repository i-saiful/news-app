package com.saiful.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.saiful.newsapp.R
import com.saiful.newsapp.constant.Constant
import com.saiful.newsapp.databinding.ActivityMainBinding
import com.saiful.newsapp.worker.NewsApiCallWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        set global context view
        Constant.contextView = binding.navHostFragment
//        set global context
        Constant.context = applicationContext

//        navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.bookmarkFragment)
        )
        navController = navHostFragment.findNavController()
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(NewsApiCallWorker::class.java, 5, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "periodicNewsAPICall",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
//        one time call
//        val work = OneTimeWorkRequestBuilder<NewsApiCallWorker>()
//            .build()
//        WorkManager.getInstance(this).enqueue(work)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}