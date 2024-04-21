package com.fadlurahmanf.monorepo.app_example.domain.usecases

import android.content.Context
import android.util.Log
import co.id.fadlurahmanf.core_call_notification.domain.plugins.CallNotification
import com.fadlurahmanf.monorepo.app_notification.domain.services.AppCallNotificationPlayer
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

    override fun showSimpleNotification(context: Context) =
        appNotificationRepository.showNotification(
            context,
            id = 1,
            title = "Simple Notification",
            message = "This is Example of Simple Notification"
        )

    override fun showMessagingNotification(context: Context) =
        appNotificationRepository.showMessagingNotification(context, 2)

    override fun showIncomingCallNotification(context: Context) {
        appNotificationRepository.askPermission(context, onCompleteCheckPermission = { isGranted ->
            if (isGranted) {
                Log.d(
                    ExampleNotificationUseCaseImpl::class.java.simpleName,
                    "show incoming call notification permission is granted"
                )
                appNotificationRepository.createVOIPChannel(context)

                CallNotification.showIncomingCallNotification(
                    context,
                    callNotificationId = 1,
                    callerName = "Taufik Fadlurahman Fajari",
                    callerNetworkImage = "https://raw.githubusercontent.com/TutorialsBuzz/cdn/main/android.jpg",
                    AppCallNotificationPlayer::class.java
                )
            } else {
                Log.d(
                    ExampleNotificationUseCaseImpl::class.java.simpleName,
                    "unable to showIncomingCallNotification because permission is not granted"
                )
            }
        })
    }

    override fun startIncomingCallPlayer(context: Context) {}

    override fun stopIncomingCallPlayer(context: Context) {}

}
