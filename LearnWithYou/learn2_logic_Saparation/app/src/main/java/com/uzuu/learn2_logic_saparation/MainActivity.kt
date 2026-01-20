package com.uzuu.learn2_logic_saparation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uzuu.learn2_logic_saparation.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
// =========================== bài 2 ==================================================
//    private lateinit var binding: ActivityMainBinding
//    private val logic = MainLogic()
//
//    override fun onCreate(saveInstanceState: Bundle?){
//        super.onCreate(saveInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnGreet.setOnClickListener {
//            var name = binding.edtName.text.toString()
//            val message = logic.getGreeting(name)
//            binding.tvResult.text = message
//        }
//    }
// =========================================================================================

// =========================== bài 3 ==================================================
        private lateinit var binding: ActivityMainBinding
        private val logic = MainLogic()

        private var currentMessage: String = ""   // <-- STATE: message hiện tại

    //savedInstanceState gióng kiểu 1 cái túi để lưu dữ liệu khôi phục khi act bị giết
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // 1) Restore state (nếu có)
        //Lấy message cũ từ túi (nếu có) và gán lại vào biến state
            currentMessage = savedInstanceState?.getString(KEY_MESSAGE).orEmpty()
        //Render lại UI theo state đó
            binding.tvResult.text = if (currentMessage.isEmpty()) "Kết quả sẽ hiện ở đây" else currentMessage

            // 2) Event -> update state -> render UI
            binding.btnGreet.setOnClickListener {
                val name = binding.edtName.text.toString()
                val message = logic.getGreeting(name) // hoặc getgreeting tuỳ bạn
                currentMessage = message               // update STATE
                binding.tvResult.text = currentMessage // render UI
            }
        }

    //trước khi chết, muốn lưu gì thì nhét vào túi outstate
        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            // Save state
        // lưu dạng key-value
            outState.putString(KEY_MESSAGE, currentMessage)
        }

    // khai báo biến static final
        companion object {
            private const val KEY_MESSAGE = "KEY_MESSAGE"
        }


// =========================================================================================

}