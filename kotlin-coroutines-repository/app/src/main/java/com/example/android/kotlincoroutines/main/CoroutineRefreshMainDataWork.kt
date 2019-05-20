package com.example.android.kotlincoroutines.main

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class CoroutineRefreshMainDataWork (context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    // returns a state from result
    override suspend fun doWork(): Result {
        val db = getDatabase(applicationContext)
        val repository = TitleRepository(MainNetworkImpl, db.titleDao)

        return try {
            repository.refreshTitleCoroutine()
            Result.success()
        } catch (error: TitleRefreshError) {
            Result.failure()
        }
    }

}