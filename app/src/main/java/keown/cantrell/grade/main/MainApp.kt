package keown.cantrell.grade.main

import android.app.Application
import keown.cantrell.grade.models.GradingJSONStore
import keown.cantrell.grade.models.GradingMemStore
import keown.cantrell.grade.models.GradingModel
import keown.cantrell.grade.models.GradingStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var gradings: GradingStore

    override fun onCreate() {
        super.onCreate()
        gradings = GradingJSONStore(applicationContext)
        info("Grading started")
    }
}

