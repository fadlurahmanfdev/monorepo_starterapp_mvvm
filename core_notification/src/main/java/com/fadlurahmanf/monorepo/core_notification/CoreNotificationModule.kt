package com.fadlurahmanf.monorepo.core_notification

import com.github.fadlurahmanfdev.core_notification.data.repositories.NotificationRepository
import com.github.fadlurahmanfdev.core_notification.data.repositories.NotificationRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class CoreNotificationModule {
    @Provides
    fun provideNotificationRepository(): NotificationRepository {
        return NotificationRepositoryImpl()
    }
}