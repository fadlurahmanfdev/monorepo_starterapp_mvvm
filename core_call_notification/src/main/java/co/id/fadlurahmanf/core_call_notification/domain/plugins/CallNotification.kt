package co.id.fadlurahmanf.core_call_notification.domain.plugins

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import co.id.fadlurahmanf.core_call_notification.domain.receivers.CallNotificationReceiver
import co.id.fadlurahmanf.core_call_notification.domain.services.CallNotificationPlayer

class CallNotification {
    companion object {
        private fun getFlagPendingIntent(): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        }

        fun getFullScreenActivityPendingIntent(
            context: Context,
            callNotificationId: Int,
            clazz: Class<*>
        ): PendingIntent {
            val intent = Intent(context, clazz)
            intent.apply {
                putExtra(CallNotificationReceiver.PARAM_CALL_NOTIFICATION_ID, callNotificationId)
            }
            return PendingIntent.getActivity(context, 0, intent, getFlagPendingIntent())
        }

        fun acceptIncomingCallPendingIntent(
            context: Context,
            callNotificationId: Int,
            clazz: Class<*>
        ): PendingIntent {
            val intent = Intent(context, clazz)
            intent.apply {
                action = CallNotificationReceiver.ACCEPT_INCOMING_CALL
                putExtra(CallNotificationReceiver.PARAM_CALL_NOTIFICATION_ID, callNotificationId)
            }
            return PendingIntent.getBroadcast(context, 0, intent, getFlagPendingIntent())
        }

        fun declineIncomingCallPendingIntent(
            context: Context,
            callNotificationId: Int,
            clazz: Class<*>
        ): PendingIntent {
            val intent = Intent(context, clazz)
            intent.apply {
                action = CallNotificationReceiver.DECLINE_INCOMING_CALL
                putExtra(CallNotificationReceiver.PARAM_CALL_NOTIFICATION_ID, callNotificationId)
            }
            return PendingIntent.getBroadcast(context, 0, intent, getFlagPendingIntent())
        }

        fun acceptIncomingCall(
            context: Context,
            callNotificationId: Int,
            clazz: Class<*>
        ) {
            val intent = Intent(context, clazz)
            intent.apply {
                action = CallNotificationPlayer.ACCEPT_INCOMING_CALL
                putExtra(CallNotificationReceiver.PARAM_CALL_NOTIFICATION_ID, callNotificationId)
            }
            context.stopService(intent)
        }

        fun declineIncomingCall(
            context: Context,
            callNotificationId: Int,
            clazz: Class<*>
        ) {
            val intent = Intent(context, clazz)
            intent.apply {
                action = CallNotificationPlayer.DECLINE_INCOMING_CALL
                putExtra(
                    CallNotificationPlayer.PARAM_CALL_NOTIFICATION_ID,
                    callNotificationId
                )
            }
            context.stopService(intent)
        }

        fun showIncomingCallNotification(
            context: Context,
            callNotificationId: Int,
            callerName: String,
            callerNetworkImage: String?,
            clazz: Class<*>
        ) {
            val intent = Intent(context, clazz)
            intent.apply {
                action = CallNotificationPlayer.SHOW_INCOMING_CALL_NOTIFICATION
                putExtra(CallNotificationPlayer.PARAM_CALL_NOTIFICATION_ID, callNotificationId)
                putExtra(CallNotificationPlayer.PARAM_CALLER_NAME, callerName)
                putExtra(CallNotificationPlayer.PARAM_CALLER_NETWORK_IMAGE, callerNetworkImage)
            }
            return ContextCompat.startForegroundService(context, intent)
        }
    }

}