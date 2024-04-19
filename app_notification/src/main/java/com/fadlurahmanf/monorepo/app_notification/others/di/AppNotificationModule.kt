package com.fadlurahmanf.monorepo.app_notification.others.di

import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepository
import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepositoryImpl
import com.fadlurahmanf.monorepo.core_notification.NotificationModule
import com.github.fadlurahmanfdev.core_notification.data.repositories.NotificationRepository
import dagger.Module
import dagger.Provides

@Module(includes = [NotificationModule::class])
class AppNotificationModule {
    @Provides
    fun provideAppNotificationRepository(notificationRepository: NotificationRepository): AppNotificationRepository {
        return AppNotificationRepositoryImpl(notificationRepository)
    }
}