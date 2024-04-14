package com.fadlurahmanf.monorepo.app_example.others.di

import com.fadlurahmanf.monorepo.core_crypto.others.di.CryptoModule
import dagger.Module

@Module(includes = [CryptoModule::class])
class AppExampleModule {}