package com.fadlurahmanf.monorepo.app_example.presentation.features.crypto

import com.fadlurahmanf.monorepo.app_shared.activity.BaseViewModel
import com.github.fadlurahmanfdev.core_crypto.data.repositories.CryptoRSARepository
import javax.inject.Inject

class ExampleCryptoViewModel @Inject constructor(
    private val cryptoRSARepository: CryptoRSARepository
) : BaseViewModel() {
    fun generateKey() = cryptoRSARepository.generateKey()
}