package com.uzuu.learn5_livedata

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.uzuu.learn5_livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        //set ui
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //observe state
        viewModel.message.observe(this) { message ->
            binding.tvResult.text = message
        }

        //gửi event cho viewmodel
        binding.btnGreet.setOnClickListener {
            var name = binding.edtName.text.toString()
            viewModel.Greet(name)
        }
    }
}
