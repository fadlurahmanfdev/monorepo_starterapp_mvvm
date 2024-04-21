package com.fadlurahmanf.monorepo.app_notification.domain.receiver

import android.content.Context
import android.content.Intent
import co.id.fadlurahmanf.core_call_notification.domain.plugins.CallNotification
import co.id.fadlurahmanf.core_call_notification.domain.receivers.CallNotificationReceiver
import com.fadlurahmanf.monorepo.app_notification.domain.services.AppCallNotificationPlayer
import com.fadlurahmanf.monorepo.app_notification.presentation.IncomingCallActivity

class AppCallNotificationReceiver : CallNotificationReceiver() {
    override fun onAcceptIncomingCall(context: Context, callNotificationId: Int) {
        val intent = Intent(context, IncomingCallActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)

        CallNotification.acceptIncomingCall(
            context,
            callNotificationId = callNotificationId,
            clazz = AppCallNotificationPlayer::class.java
        )
    }

    override fun onDeclinedIncomingCall(context: Context, callNotificationId: Int) {
        CallNotification.declineIncomingCall(
            context,
            callNotificationId = callNotificationId,
            clazz = AppCallNotificationPlayer::class.java
        )
    }
}