package com.example.cleanarchitectureplayground.ui.application

import android.content.Context
import androidx.work.WorkManager
import com.example.data.AppDataModule
import com.example.data.network.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Clock
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        AppDataModule::class,
        NetworkModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
object MyApplicationModule {

    @Provides
    fun provideClock(): Clock = Clock.systemDefaultZone()

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}