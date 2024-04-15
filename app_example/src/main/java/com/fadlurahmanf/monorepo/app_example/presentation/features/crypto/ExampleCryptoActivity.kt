package com.fadlurahmanf.monorepo.app_example.presentation.features.crypto

import android.os.Bundle
import android.util.Log
import com.fadlurahmanf.monorepo.app_example.R
import com.fadlurahmanf.monorepo.app_example.data.dto.FeatureModel
import com.fadlurahmanf.monorepo.app_example.databinding.ActivityExampleCryptoBinding
import com.fadlurahmanf.monorepo.app_example.presentation.BaseExampleActivity
import com.fadlurahmanf.monorepo.app_example.presentation.utilities.ListExampleAdapter
import javax.inject.Inject

class ExampleCryptoActivity :
    BaseExampleActivity<ActivityExampleCryptoBinding>(ActivityExampleCryptoBinding::inflate),
    ListExampleAdapter.Callback {

    @Inject
    lateinit var viewModel: ExampleCryptoViewModel

    override fun onBaseExampleInjectActivity() {
        component.inject(this)
    }

    private val features: List<FeatureModel> = listOf<FeatureModel>(
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Generate RSA Key",
            desc = "Generate RSA Key",
            enum = "GENERATE_RSA_KEY"
        ),
    )

    private lateinit var adapter: ListExampleAdapter
    override fun onBaseExampleCreate(savedInstanceState: Bundle?) {
        setOnApplyWindowInsetsListener(binding.main)
        binding.rv.setItemViewCacheSize(features.size)
        binding.rv.setHasFixedSize(true)

        adapter = ListExampleAdapter()
        adapter.setCallback(this)
        adapter.setList(features)
        adapter.setHasStableIds(true)
        binding.rv.adapter = adapter
    }

    override fun onClicked(item: FeatureModel) {
        when (item.enum) {
            "GENERATE_RSA_KEY" -> {
                val key = viewModel.generateKey()
                Log.d(
                    "BaseLoggerTAG", "PRIVATE KEY: ${key.privateKey}" +
                            "\n\n-----------------\n\n" +
                            "PUBLIC KEY: ${key.publicKey}"
                )
            }
        }
    }

}