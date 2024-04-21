package co.id.fadlurahmanf.core_call_notification

import co.id.fadlurahmanf.core_call_notification.data.repositories.CallNotificationRepository
import co.id.fadlurahmanf.core_call_notification.data.repositories.CallNotificationRepositoryImpl
import com.github.fadlurahmanfdev.core_notification.data.repositories.NotificationRepository
import dagger.Module
import dagger.Provides

@Module
class CoreCallNotificationModule {

    @Provides
    fun provideCallNotificationRepository(notificationRepository: NotificationRepository): CallNotificationRepository {
        return CallNotificationRepositoryImpl(notificationRepository)
    }
}