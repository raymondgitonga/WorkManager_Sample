package com.tosh.workmanager_sample

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.tosh.workmanager_sample.workers.FirstWorker
import com.tosh.workmanager_sample.workers.SecondWorker

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val workManager = WorkManager.getInstance(applicationContext)
            var firstWorkRequest = OneTimeWorkRequest.Builder(FirstWorker::class.java).build()
            var secondWorkRequest = OneTimeWorkRequest.Builder(SecondWorker::class.java)
                .addTag("SECOND_TAG").build()

//            workManager.enqueue(firstWorkRequest)

            workManager.beginWith(firstWorkRequest).then(secondWorkRequest).enqueue()

            workManager.getWorkInfoByIdLiveData(firstWorkRequest.id).observe(this, Observer { workInfo ->
                var fwStatus = workInfo.state

                Toast.makeText(this, fwStatus.name, Toast.LENGTH_SHORT).show()
            })

            workManager.getWorkInfosByTagLiveData("SECOND_TAG").observe(this, Observer {workInfoList ->
                var swStatus = workInfoList[0].state

                Toast.makeText(this, swStatus.name, Toast.LENGTH_SHORT).show()
            })
        }
    }

}
