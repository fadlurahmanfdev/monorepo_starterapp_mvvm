package co.id.fadlurahmanf.core_call_notification.data.repositories

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.IconCompat

interface CallNotificationRepository {
    fun createVOIPChannel(context: Context)

    fun getIncomingCallNotification(
        context: Context,
        id: Int,
        channelId: String,
        callerName: String,
        callerIcon: IconCompat?,
        @DrawableRes smallIcon: Int,
        fullScreenIntent: PendingIntent,
        acceptCallIndent: PendingIntent,
        declinedCallIntent: PendingIntent,
    ): Notification

    fun showIncomingCallNotification(
        context: Context,
        id: Int,
        callerName: String,
        phoneNumberCaller: String,
        acceptCallText: String,
        declinedCallText: String,
        @DrawableRes smallIcon: Int,
        fullScreenIntent: PendingIntent,
        deletePendingIntent: PendingIntent
    )

    fun cancelIncomingCallNotification(context: Context, id: Int)
}