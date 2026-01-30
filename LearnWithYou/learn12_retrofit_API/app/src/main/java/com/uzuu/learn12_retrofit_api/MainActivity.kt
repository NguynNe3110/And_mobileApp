package com.uzuu.learn12_retrofit_api

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.uzuu.learn12_retrofit_api.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.xml.sax.Parser

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoad.setOnClickListener {
//            val id: Int? = binding.numberId.text.toString().toIntOrNull()
            binding.numberId.text.toString().toIntOrNull()?.let { id ->
                viewModel.loadUser(id)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.uiState.collect { state ->
                        binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                        binding.tvResult.text = state.text
                        binding.btnLoad.isEnabled = !state.isLoading
                    }
                }

                launch {
                    viewModel.uiEvent.collect { e->
                        when(e){
                            is MainUiEvent.Toast ->
                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}