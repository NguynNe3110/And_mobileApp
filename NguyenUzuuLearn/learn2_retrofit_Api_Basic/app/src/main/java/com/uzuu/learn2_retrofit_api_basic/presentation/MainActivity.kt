package com.uzuu.learn2_retrofit_api_basic.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.uzuu.learn2_retrofit_api_basic.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlin.toString

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoad.setOnClickListener {
            binding.numberId.text.toString().toIntOrNull()?.let { id ->
                viewModel.onLoadingUser(id)
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                        binding.tvResult.text = state.result
                        binding.btnLoad.isEnabled = !state.isLoading
                    }
                }

                launch {
                    viewModel.uiEvent.collect { e->
                        when (e) {
                            is MainUiEvent.Toast -> {
                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}