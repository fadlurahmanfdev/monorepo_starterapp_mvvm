package com.fadlurahmanf.monorepo.app_notification.data.repositories

import android.content.Context
import android.media.RingtoneManager
import androidx.annotation.DrawableRes
import com.fadlurahmanf.monorepo.app_notification.R
import com.fadlurahmanf.monorepo.core_notification.data.repositories.NotificationRepository
import com.fadlurahmanf.monorepo.core_notification.data.repositories.NotificationRepositoryImpl
import javax.inject.Inject

class AppNotificationRepositoryImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : AppNotificationRepository {
    companion object {
        @DrawableRes
        val BANK_MAS_LOGO_ICON = R.drawable.il_logo_bankmas
        const val GENERAL_CHANNEL_ID = NotificationRepositoryImpl.GENERAL_CHANNEL_ID
        const val GENERAL_CHANNEL_NAME = NotificationRepositoryImpl.GENERAL_CHANNEL_NAME
        const val GENERAL_CHANNEL_DESCRIPTION =
            NotificationRepositoryImpl.GENERAL_CHANNEL_DESCRIPTION
    }

    private fun createGeneralChannel(context: Context) {
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        notificationRepository.createNotificationChannel(
            context,
            channelId = GENERAL_CHANNEL_ID,
            channelName = GENERAL_CHANNEL_NAME,
            channelDescription = GENERAL_CHANNEL_DESCRIPTION,
            sound = sound,
        )
    }

    override fun showNotification(context: Context, id: Int, title: String, message: String) {
        createGeneralChannel(context)
        notificationRepository.showGeneralNotification(
            context,
            id = id,
            title = title,
            message = message,
            smallIcon = BANK_MAS_LOGO_ICON,
            groupKey = null,
            pendingIntent = null
        )
    }
}