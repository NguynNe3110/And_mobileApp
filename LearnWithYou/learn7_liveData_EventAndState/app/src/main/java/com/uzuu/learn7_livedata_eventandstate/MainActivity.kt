package com.uzuu.learn7_livedata_eventandstate

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.uzuu.learn7_livedata_eventandstate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        //set ui
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.message.observe(this) {
            binding.tvResult.text = it
        }

// =========================== ❌ SAI: dùng LiveData cho EVENT ================================

//        viewModel.toast.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//
//        }
//
//        //gửi event cho viewmodel
//        binding.btnGreet.setOnClickListener {
//            var name = binding.edtName.text.toString()
//            viewModel.greet(name)
//        }
// =========================================================================================

// =========================== Cách 1 (CỔ ĐIỂN – dễ hiểu): EventWrapper =============================
        viewModel.toastEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGreet.setOnClickListener {
            var name = binding.edtName.text.toString()
            viewModel.greet(name)
        }
// =========================================================================================
// =============== Cách 2 (HIỆN ĐẠI): StateFlow / SharedFlow =================================
// Observe STATE
//        viewModel.message.observe(this) {
//            binding.tvResult.text = it
//        }

// Observe EVENT
//        viewModel.toastEvent.observe(this) { event ->
//            event.getContentIfNotHandled()?.let {
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            }
//        }

// =========================================================================================

    }
}
