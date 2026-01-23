package com.uzuu.learn8_livedata_stateflow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.uzuu.learn8_livedata_stateflow.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            viewModel.onSearchClick(binding.edtQuery.text.toString())
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // 1) STATE: UI chỉ collect 1 luồng uiState
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progress.visibility =
                            if (state.isLoading) View.VISIBLE else View.GONE

                        binding.btnSearch.isEnabled = state.isButtonEnabled
                        binding.tvResult.text = state.resultText
                    }
                }

                // 2) EVENT: toast / navigate… chỉ 1 lần
                launch {
                    viewModel.event.collect { e ->
                        when (e) {
                            is UiEvent.Toast ->
                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
