package com.fadlurahmanf.monorepo.app_notification.domain.services


import android.app.Notification
import co.id.fadlurahmanf.core_call_notification.data.repositories.CallNotificationRepository
import co.id.fadlurahmanf.core_call_notification.data.repositories.CallNotificationRepositoryImpl
import co.id.fadlurahmanf.core_call_notification.domain.services.CallNotificationPlayer
import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepository
import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepositoryImpl
import com.github.fadlurahmanfdev.core_notification.data.repositories.NotificationRepository
import com.github.fadlurahmanfdev.core_notification.data.repositories.NotificationRepositoryImpl

class AppCallNotificationPlayer : CallNotificationPlayer() {
    private lateinit var notificationRepository: NotificationRepository
    private lateinit var callNotificationRepository: CallNotificationRepository
    private lateinit var appNotificationRepository: AppNotificationRepository
    override fun onCreate() {
        super.onCreate()
        notificationRepository = NotificationRepositoryImpl()
        callNotificationRepository = CallNotificationRepositoryImpl(notificationRepository)
        appNotificationRepository = AppNotificationRepositoryImpl(
            notificationRepository = notificationRepository,
            callNotificationRepository = callNotificationRepository
        )
    }

    override fun onGetIncomingCallNotification(
        callNotificationId: Int,
        callerName: String,
        callerImage: String?
    ): Notification {
        return appNotificationRepository.getIncomingCallNotification(
            applicationContext,
            callerName = callerName,
            callerImage = callerImage
        )
    }
}