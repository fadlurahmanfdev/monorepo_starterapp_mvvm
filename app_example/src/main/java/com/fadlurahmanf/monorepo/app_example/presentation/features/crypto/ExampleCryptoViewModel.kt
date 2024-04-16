package com.fadlurahmanf.monorepo.app_example.presentation.features.crypto

import com.fadlurahmanf.monorepo.app_shared.activity.BaseViewModel
import com.fadlurahmanf.monorepo.core_crypto.data.repositories.CryptoRSAV2Repository
import javax.inject.Inject

class ExampleCryptoViewModel @Inject constructor(
    private val cryptoRSAV2Repository: CryptoRSAV2Repository
) : BaseViewModel() {
    fun generateKey() = cryptoRSAV2Repository.generateKey()
}