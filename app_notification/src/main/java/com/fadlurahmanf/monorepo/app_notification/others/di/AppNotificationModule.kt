package com.fadlurahmanf.monorepo.app_notification.others.di

import co.id.fadlurahmanf.core_call_notification.CoreCallNotificationModule
import co.id.fadlurahmanf.core_call_notification.data.repositories.CallNotificationRepository
import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepository
import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepositoryImpl
import com.fadlurahmanf.monorepo.core_notification.CoreNotificationModule
import com.github.fadlurahmanfdev.core_notification.data.repositories.NotificationRepository
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        CoreNotificationModule::class,
        CoreCallNotificationModule::class,
    ]
)
class AppNotificationModule {
    @Provides
    fun provideAppNotificationRepository(
        notificationRepository: NotificationRepository,
        callNotificationRepository: CallNotificationRepository
    ): AppNotificationRepository {
        return AppNotificationRepositoryImpl(notificationRepository, callNotificationRepository)
    }
}