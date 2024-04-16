package com.fadlurahmanf.monorepo.app_example

import com.fadlurahmanf.monorepo.app_example.domain.usecases.ExampleNotificationUseCase
import com.fadlurahmanf.monorepo.app_example.domain.usecases.ExampleNotificationUseCaseImpl
import com.fadlurahmanf.monorepo.app_notification.data.repositories.AppNotificationRepository
import com.fadlurahmanf.monorepo.app_notification.others.di.AppNotificationModule
import com.fadlurahmanf.monorepo.core_crypto.CryptoModule
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        CryptoModule::class,
        AppNotificationModule::class,
    ]
)
class AppExampleModule {
    @Provides
    fun provideExampleNotificationUseCase(appNotificationRepository: AppNotificationRepository): ExampleNotificationUseCase {
        return ExampleNotificationUseCaseImpl(appNotificationRepository)
    }
}