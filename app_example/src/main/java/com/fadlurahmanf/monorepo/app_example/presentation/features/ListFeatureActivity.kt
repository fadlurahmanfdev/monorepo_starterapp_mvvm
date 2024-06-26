package com.fadlurahmanf.monorepo.app_example.presentation.features

import android.content.Intent
import android.os.Bundle
import com.fadlurahmanf.monorepo.app_example.R
import com.fadlurahmanf.monorepo.app_example.data.dto.FeatureModel
import com.fadlurahmanf.monorepo.app_example.databinding.ActivityListFeatureBinding
import com.fadlurahmanf.monorepo.app_example.presentation.BaseExampleActivity
import com.fadlurahmanf.monorepo.app_example.presentation.features.crypto.ExampleCryptoActivity
import com.fadlurahmanf.monorepo.app_example.presentation.features.notification.ExampleNotificationActivity
import com.fadlurahmanf.monorepo.app_example.presentation.utilities.ListExampleAdapter

class ListFeatureActivity :
    BaseExampleActivity<ActivityListFeatureBinding>(ActivityListFeatureBinding::inflate),
    ListExampleAdapter.Callback {
    override fun onBaseExampleInjectActivity() {
        component.inject(this)
    }

    private val features: List<FeatureModel> = listOf<FeatureModel>(
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Crypto",
            desc = "Feature of Crypto",
            enum = "CRYPTO"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Notification",
            desc = "Feature of Notification",
            enum = "NOTIFICATION"
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
            "CRYPTO" -> {
                val intent = Intent(this, ExampleCryptoActivity::class.java)
                startActivity(intent)
            }
            "NOTIFICATION" -> {
                val intent = Intent(this, ExampleNotificationActivity::class.java)
                startActivity(intent)
            }
        }
    }

}