package com.fadlurahmanf.starterappmvvm.core.notification.others

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.fadlurahmanf.starterappmvvm.R

abstract class BaseNotificationService {
    private lateinit var notificationManager: NotificationManager

    companion object {
        const val VOIP_CHANNEL_ID = "VOIP"
        const val VOIP_CHANNEL_NAME = "Panggilan"
        const val VOIP_CHANNEL_DESCRIPTION = "Panggilan"
        const val MEDIA_CHANNEL_ID = "MEDIA"
        const val MEDIA_CHANNEL_NAME = "Media"
        const val MEDIA_CHANNEL_DESCRIPTION = "Media"
    }

    fun getNotificationManager(context: Context): NotificationManager {
        if (!this::notificationManager.isInitialized) {
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager
    }

    fun createVoipNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val allChannels = getNotificationManager(context).notificationChannels
            var voipChannel: NotificationChannel? = null
            for (element in allChannels) {
                if (element.id == VOIP_CHANNEL_ID) {
                    voipChannel = element
                    break
                }
            }
            if (voipChannel != null) return
            val channel = NotificationChannel(
                VOIP_CHANNEL_ID,
                VOIP_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                this.description = VOIP_CHANNEL_DESCRIPTION
                setSound(null, null)
            }
            getNotificationManager(context).createNotificationChannel(channel)
        }
    }

    fun createMediaNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val allChannels = getNotificationManager(context).notificationChannels
            var mediaChannel: NotificationChannel? = null
            for (element in allChannels) {
                if (element.id == MEDIA_CHANNEL_ID) {
                    mediaChannel = element
                    break
                }
            }
            if (mediaChannel != null) return
            val channel = NotificationChannel(
                MEDIA_CHANNEL_ID,
                MEDIA_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                this.description = MEDIA_CHANNEL_DESCRIPTION
                setSound(null, null)
            }
            getNotificationManager(context).createNotificationChannel(channel)
        }
    }

    @Deprecated(
        "deprecated because it is hardcoded small icon",
        replaceWith = ReplaceWith("notificationBuilderV2")
    )
    fun notificationBuilder(
        context: Context,
        channelId: String
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.il_logo_bankmas) // TODO(DEV): Change Small Icon
    }

    fun notificationBuilderV2(
        context: Context,
        channelId: String,
        @DrawableRes smallIcon: Int,
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSmallIcon(smallIcon)
    }

    fun groupNotificationBuilder(
        context: Context,
        channelId: String,
        groupKey: String,
        bigContentTitle: String,
        summaryText: String,
        lines: List<String>,
        @DrawableRes smallIcon: Int,
    ): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, channelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSmallIcon(smallIcon)

        val inboxStyle = NotificationCompat.InboxStyle()
        if (lines.isNotEmpty()) {
            for (element in lines) {
                inboxStyle.addLine(element)
            }
            inboxStyle.setBigContentTitle(bigContentTitle)
                .setSummaryText(summaryText)
        }
        return builder.setStyle(inboxStyle)
            .setGroup(groupKey)
            .setGroupSummary(true)
    }
}