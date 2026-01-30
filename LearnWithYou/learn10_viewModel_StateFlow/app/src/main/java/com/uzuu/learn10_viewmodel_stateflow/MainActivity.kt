package com.uzuu.learn10_viewmodel_stateflow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.uzuu.learn10_viewmodel_stateflow.databinding.ActivityCounterProBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCounterProBinding
    private val viewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCounterProBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIncrease.setOnClickListener { viewModel.onPlus() }
        binding.btnDecrease.setOnClickListener { viewModel.onMinus() }
        binding.btnUndo.setOnClickListener { viewModel.onUndo() }
        binding.btnReset.setOnClickListener { viewModel.onReset() }

        // Switch auto
        binding.swMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAutoToggle(isChecked)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // collect state
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progress.visibility =
                            if (state.isLoading) View.VISIBLE else View.GONE

                        binding.tvCount.text = state.intCount.toString()
                        binding.tvState.text = state.tvState

                        // đồng bộ switch theo state (tránh lệch)
                        if (binding.swMode.isChecked != state.isAuto) {
                            binding.swMode.isChecked = state.isAuto
                        }

                        // disable khi loading
                        val enabled = !state.isLoading
                        binding.btnIncrease.isEnabled = enabled
                        binding.btnDecrease.isEnabled = enabled
                        binding.btnReset.isEnabled = enabled
                        binding.btnUndo.isEnabled = enabled && state.canUndo
                    }
                }

                // collect event
                launch {
                    viewModel.uiEvent.collect { e ->
                        when (e) {
                            is UiEvent.Toast ->
                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                }
            }
        }
    }
}
