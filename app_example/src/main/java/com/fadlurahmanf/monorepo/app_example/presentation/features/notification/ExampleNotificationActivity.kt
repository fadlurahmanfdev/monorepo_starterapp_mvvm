package com.fadlurahmanf.monorepo.app_example.presentation.features.notification

import android.os.Bundle
import com.fadlurahmanf.monorepo.app_example.R
import com.fadlurahmanf.monorepo.app_example.data.dto.FeatureModel
import com.fadlurahmanf.monorepo.app_example.databinding.ActivityExampleNotificationBinding
import com.fadlurahmanf.monorepo.app_example.presentation.BaseExampleActivity
import com.fadlurahmanf.monorepo.app_example.presentation.utilities.ListExampleAdapter
import javax.inject.Inject

class ExampleNotificationActivity :
    BaseExampleActivity<ActivityExampleNotificationBinding>(ActivityExampleNotificationBinding::inflate),
    ListExampleAdapter.Callback {
    @Inject
    lateinit var viewModel: ExampleNotificationViewModel

    override fun onBaseExampleInjectActivity() {
        component.inject(this)
    }

    private val features: List<FeatureModel> = listOf<FeatureModel>(
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Ask Permission",
            desc = "Ask Permission Notification",
            enum = "ASK_PERMISSION"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Show Notification",
            desc = "Show Notification",
            enum = "SHOW_NOTIFICATION"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Show Messaging Notification",
            desc = "Show Notification With Messaging Style",
            enum = "SHOW_MESSAGING_NOTIFICATION"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Show Incoming Call Notification",
            desc = "Show Notification With Call Style",
            enum = "SHOW_INCOMING_CALL_NOTIFICATION"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Start Incoming Call Player",
            desc = "Start Incoming Call Player",
            enum = "START_INCOMING_CALL_PLAYER"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Stop Incoming Call Player",
            desc = "Stop Incoming Call Player",
            enum = "STOP_INCOMING_CALL_PLAYER"
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
            "ASK_PERMISSION" -> {
                viewModel.askPermission(this)
            }

            "SHOW_NOTIFICATION" -> {
                viewModel.showNotification(this)
            }

            "SHOW_MESSAGING_NOTIFICATION" -> {
                viewModel.showMessagingNotification(this)
            }

            "SHOW_INCOMING_CALL_NOTIFICATION" -> {
                viewModel.showIncomingCallNotification(this)
            }

            "START_INCOMING_CALL_PLAYER" -> {
                viewModel.startIncomingCallPlayer(this)
            }

            "STOP_INCOMING_CALL_PLAYER" -> {
                viewModel.stopIncomingCallPlayer(this)
            }
        }
    }

}