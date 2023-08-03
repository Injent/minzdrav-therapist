package ru.minzdrav.therapist.sync.initializers

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class SyncInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val workerFactory = getWorkerFactory(context)

        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(context, config)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        emptyList()

    private fun getWorkerFactory(appContext: Context): HiltWorkerFactory {
        return EntryPointAccessors.fromApplication(
            appContext,
            WorkManagerInitializerEntryPoint::class.java
        ).hiltWorkerFactory()
    }

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface WorkManagerInitializerEntryPoint {
        fun hiltWorkerFactory(): HiltWorkerFactory
    }
}