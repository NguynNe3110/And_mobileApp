package com.uzuu.first_counter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.uzuu.first_counter.databinding.ActivityCounterBinding
import kotlinx.coroutines.launch

class CounterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCounterBinding
    private val viewModel : CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIncrease.setOnClickListener { viewModel.onPlus() }
        binding.btnDecrease.setOnClickListener { viewModel.onMinus() }
        binding.btnReset.setOnClickListener { viewModel.onReset() }
        binding.btnUndo.setOnClickListener { viewModel.onUndo() }

        binding.swMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAuto(isChecked)
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){

                launch {
                    viewModel.uiState.collect { state->
                        binding.progress.visibility =
                            if(state.isLoading) View.VISIBLE else View.GONE
                        binding.tvCount.text = state.count.toString()
                        binding.tvState.text = state.state

                        if(binding.swMode.isChecked != state.isAuto){
                            binding.swMode.isChecked = state.isAuto
                        }

                        val enabled = !state.isLoading
                        binding.btnIncrease.isEnabled = enabled
                        binding.btnDecrease.isEnabled = enabled
                        binding.btnReset.isEnabled = enabled
                        //hay pheest
                        binding.btnUndo.isEnabled = enabled && state.canUndo
                    }
                }

                launch {
                    viewModel.uiEvent.collect { e ->
                        when (e) {
                            is uiCounterEvent.Toast ->
                                Toast.makeText(this@CounterActivity, e.message, Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                }
            }
        }
    }
}