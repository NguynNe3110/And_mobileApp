package com.uzuu.learn4_viewmodel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.uzuu.learn4_viewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //by viewModels() = “Android, hãy cấp cho tôi 1 ViewModel đúng chuẩn. Nếu Activity bị tạo lại thì trả lại đúng ViewModel cũ (trong cùng scope).”
    //by viewModels() đảm bảo ViewModel được lấy từ ViewModelStore (kho lưu ViewModel gắn với Activity/Fragment).
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // nạp ui (learn1)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // khôi phục dữ liệu từ state
        binding.tvResult.text =
            if(viewModel.message.isEmpty()) "Ket qua se hien thi o day"
                else viewModel.message

        // xử lý theo kiểu bán mvvm
        binding.btnGreet.setOnClickListener{
            var name = binding.edtName.text.toString()
            viewModel.greet(name)
            binding.tvResult.text = viewModel.message
        }
    }
}
