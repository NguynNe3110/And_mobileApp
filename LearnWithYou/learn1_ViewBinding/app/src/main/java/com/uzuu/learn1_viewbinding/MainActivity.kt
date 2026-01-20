package com.uzuu.learn1_viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uzuu.learn1_viewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    //private var name: String ?= ""
    private var count: Int = 0

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)

        // 1) "Bọc" layout XML thành biến binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 2) Hiển thị UI lên màn hình
        setContentView(binding.root)

        binding.tvMessage.setText("NguyenUzuu")

        // 3) Bắt sự kiện bấm nút
        binding.btnChange.setOnClickListener {
            count++
            binding.tvMessage.setText("bạn đã nhấn $count lần!")
        }
    }
}
