package com.fadlurahmanf.monorepo.app_example.domain.usecases

import android.content.Context
import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepository
import javax.inject.Inject

class ExampleNotificationUseCaseImpl @Inject constructor(
    private val appNotificationRepository: AppNotificationRepository
) : ExampleNotificationUseCase {
    override fun showSimpleNotification(context: Context) {
        appNotificationRepository.showNotification(
            context,
            id = 1,
            title = "Simple Notification",
            message = "This is Example of Simple Notification"
        )
    }

}
