package com.tosh.workmanager_sample.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tosh.workmanager_sample.makeNotification
import com.tosh.workmanager_sample.sleep


class SecondWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val TAG by lazy {
        SecondWorker::class.java.simpleName
    }

    override fun doWork(): Result {
        sleep()
        makeNotification("Doing second work", applicationContext)

        return try {
            //Actual work
            sleep()
            makeNotification("Completed second work", applicationContext)
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error doing second work", throwable)
            Result.failure()
        }
    }
}