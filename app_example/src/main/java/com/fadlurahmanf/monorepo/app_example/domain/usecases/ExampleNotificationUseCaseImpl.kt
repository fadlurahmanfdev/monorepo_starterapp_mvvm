package com.fadlurahmanf.monorepo.app_example.domain.usecases

import android.content.Context
import android.util.Log
import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepository
import javax.inject.Inject

class ExampleNotificationUseCaseImpl @Inject constructor(
    private val appNotificationRepository: AppNotificationRepository
) : ExampleNotificationUseCase {

    override fun askPermissionNotification(
        context: Context,
        onCompleteCheckPermission: (Boolean) -> Unit
    ) {
        appNotificationRepository.askPermission(
            context,
            onCompleteCheckPermission = onCompleteCheckPermission
        )
    }

    override fun showSimpleNotification(context: Context) {
        appNotificationRepository.askPermission(context, onCompleteCheckPermission = { isGranted ->
            if (isGranted) {
                appNotificationRepository.showNotification(
                    context,
                    id = 1,
                    title = "Simple Notification",
                    message = "This is Example of Simple Notification"
                )
            } else {
                Log.d(
                    ExampleNotificationUseCaseImpl::class.java.simpleName,
                    "permission is not granted"
                )
            }
        })
    }

    override fun showMessagingNotification(context: Context, id: Int) {
        appNotificationRepository.askPermission(context, onCompleteCheckPermission = { isGranted ->
            if (isGranted) {
                appNotificationRepository.showMessagingNotification(context, id)
            } else {
                Log.d(
                    ExampleNotificationUseCaseImpl::class.java.simpleName,
                    "permission is not granted"
                )
            }
        })
    }

}
