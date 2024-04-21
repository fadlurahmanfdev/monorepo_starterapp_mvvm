package co.id.fadlurahmanf.core_call_notification.domain.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

abstract class CallNotificationReceiver : BroadcastReceiver() {
    companion object {
        const val ACCEPT_INCOMING_CALL = "com.github.fadlurahmanfdev.ACCEPT_INCOMING_CALL"
        const val DECLINE_INCOMING_CALL = "com.github.fadlurahmanfdev.DECLINE_INCOMING_CALL"
        const val PARAM_CALL_NOTIFICATION_ID = "PARAM_CALL_NOTIFICATION_ID"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        Log.d(CallNotificationReceiver::class.java.simpleName, "ACTION COMING -> ${intent?.action}")
        when (intent?.action) {
            ACCEPT_INCOMING_CALL -> {
                val callNotificationId = intent.getIntExtra(PARAM_CALL_NOTIFICATION_ID, -1)
                if (callNotificationId != -1) {
                    onAcceptIncomingCall(context, callNotificationId)
                }
            }

            DECLINE_INCOMING_CALL -> {
                val callNotificationId = intent.getIntExtra(PARAM_CALL_NOTIFICATION_ID, -1)
                if (callNotificationId != -1) {
                    onDeclinedIncomingCall(context, callNotificationId)
                }
            }
        }
    }

    abstract fun onAcceptIncomingCall(context: Context, callNotificationId: Int)

    abstract fun onDeclinedIncomingCall(context: Context, callNotificationId: Int)
}