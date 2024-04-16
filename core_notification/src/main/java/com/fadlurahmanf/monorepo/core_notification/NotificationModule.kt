package com.fadlurahmanf.monorepo.core_notification

import com.fadlurahmanf.monorepo.core_notification.data.repositories.NotificationRepository
import com.fadlurahmanf.monorepo.core_notification.data.repositories.NotificationRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class NotificationModule {
    @Provides
    fun provideNotificationService(): NotificationRepository {
        return NotificationRepositoryImpl()
    }
}