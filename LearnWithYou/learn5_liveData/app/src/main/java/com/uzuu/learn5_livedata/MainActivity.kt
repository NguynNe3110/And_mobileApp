package com.uzuu.learn5_livedata

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.uzuu.learn5_livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1️⃣ Observe state
        viewModel.message.observe(this) { message ->
            binding.tvResult.text = message
        }

        // 2️⃣ Gửi event
        binding.btnGreet.setOnClickListener {
            val name = binding.edtName.text.toString()
            viewModel.greet(name)
        }
    }
}
