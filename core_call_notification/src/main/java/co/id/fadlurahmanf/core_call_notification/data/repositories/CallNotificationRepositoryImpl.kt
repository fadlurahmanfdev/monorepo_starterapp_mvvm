package co.id.fadlurahmanf.core_call_notification.data.repositories

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import co.id.fadlurahmanf.core_call_notification.R
import com.github.fadlurahmanfdev.core_notification.data.repositories.NotificationRepository
import com.github.fadlurahmanfdev.core_notification.others.BaseNotificationService

class CallNotificationRepositoryImpl(
    private val notificationRepository: NotificationRepository
) : BaseNotificationService(), CallNotificationRepository {
    companion object {
        const val VOIP_CHANNEL_ID = "VOIP"
        const val VOIP_CHANNEL_NAME = "Panggilan"
        const val VOIP_CHANNEL_DESCRIPTION = "Notifikasi Panggilan"
    }

    override fun createVOIPChannel(context: Context) {
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        notificationRepository.createNotificationChannel(
            context,
            channelId = VOIP_CHANNEL_ID,
            channelName = VOIP_CHANNEL_NAME,
            channelDescription = VOIP_CHANNEL_DESCRIPTION,
            sound = sound,
        )
    }

    override fun getIncomingCallNotification(
        context: Context,
        id: Int,
        channelId: String,
        callerName: String,
        callerIcon: IconCompat?,
        @DrawableRes smallIcon: Int,
        fullScreenIntent: PendingIntent,
        acceptCallIndent: PendingIntent,
        declinedCallIntent: PendingIntent,
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(smallIcon)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setWhen(0)
            .setTimeoutAfter(60000L)
            .setOnlyAlertOnce(true)
            .setContentIntent(fullScreenIntent)
            .setFullScreenIntent(fullScreenIntent, true)

        val callerBuilder = Person.Builder()
            .setName(callerName)
            .setImportant(true)

        if (callerIcon != null){
            callerBuilder.setIcon(callerIcon)
        }

        val caller = callerBuilder.build()

        val style = NotificationCompat.CallStyle.forIncomingCall(
            caller,
            declinedCallIntent,
            acceptCallIndent
        )

        notificationBuilder.setStyle(style).addPerson(caller)
        val notification = notificationBuilder.build()
        notification.flags = Notification.FLAG_ONGOING_EVENT
        return notification
    }

    override fun showIncomingCallNotification(
        context: Context,
        id: Int,
        callerName: String,
        phoneNumberCaller: String,
        acceptCallText: String,
        declinedCallText: String,
        @DrawableRes smallIcon: Int,
        fullScreenIntent: PendingIntent,
        deletePendingIntent: PendingIntent
    ) {
//        notificationRepository.askNotificationPermission(
//            context,
//            onCompleteCheckPermission = { isGranted ->
//                if (isGranted) {
//                    createVOIPChannel(context)
//
//                } else {
//
//                }
//            })
        val builder = NotificationCompat.Builder(context, VOIP_CHANNEL_ID)
            .setSmallIcon(smallIcon)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setWhen(0)
            .setTimeoutAfter(60000L)
            .setOnlyAlertOnce(true)
//                        .setFullScreenIntent(getFullScreenIncomingCallActivityIntent(context, id), true)
//                        .setDeleteIntent(getDeleteCallPendingIntent(notificationId))

        val smallNotificationView = RemoteViews(
            context.packageName,
            R.layout.layout_incoming_call_notification_small
        )

        smallNotificationView.setTextViewText(
            R.id.tv_caller_name, callerName
        )
        smallNotificationView.setTextViewText(
            R.id.tv_phone_caller, phoneNumberCaller
        )
        smallNotificationView.setTextViewText(
            R.id.tv_accept_call, acceptCallText
        )
        smallNotificationView.setTextViewText(
            R.id.tv_declined_call, declinedCallText
        )

        val notificationView = RemoteViews(
            context.packageName,
            R.layout.layout_incoming_call_notification
        )

        notificationView.setTextViewText(
            R.id.tv_caller_name, callerName
        )
        notificationView.setTextViewText(
            R.id.tv_phone_caller, phoneNumberCaller
        )
        notificationView.setTextViewText(
            R.id.tv_accept_call, acceptCallText
        )
        notificationView.setTextViewText(
            R.id.tv_declined_call, declinedCallText
        )

        builder.setCustomContentView(smallNotificationView)
            .setCustomHeadsUpContentView(smallNotificationView)
            .setCustomBigContentView(notificationView)

        val notification = builder.build()
        notification.flags = Notification.FLAG_ONGOING_EVENT

        getNotificationManager(context).notify(id, notification)
    }

    override fun cancelIncomingCallNotification(context: Context, id: Int) {

    }
}