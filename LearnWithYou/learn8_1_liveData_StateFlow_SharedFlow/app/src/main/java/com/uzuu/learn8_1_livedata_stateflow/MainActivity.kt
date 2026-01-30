package com.uzuu.learn8_1_livedata_stateflow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import com.uzuu.learn8_1_livedata_stateflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        // nạp ui
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set sự kiện cho nút
        binding.btnSearch.setOnClickListener {
            viewModel.onSearchClick(binding.edtQuery.text.toString())
        }

        //lắng nghe sự thay đổi được truyền từ viewmodel, giống với observe khi dùng livedata
        // mở coroutine -> lắng nghe bằng collect
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch{
                    viewModel.uiState.collect{ state ->
                        binding.progress.visibility =
                            if(state.isLoading) View.VISIBLE else View.GONE
                        binding.tvResult.text = state.resultText
                        binding.btnSearch.isEnabled = state.isButtonEnabled
                    }
                }

                launch{
                    viewModel.event.collect { e ->
                        when(e){
                            is UiEvent.Toast ->
                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

//class MainActivity : AppCompatActivity(){
//    private lateinit var binding: ActivityMainBinding
//    private val viewModel : MainViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?){
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnSearch.setOnClickListener {
//            viewModel.onSearchClick(binding.edtQuery.text.toString())
//        }
//
//        lifecycleScope.launch{
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                launch{
//                    viewModel.uiState.collect{ state ->
//                        binding.btnSearch.isEnabled = state.isButtonEnabled
//                        binding.tvResult.text = state.resultText
//                        binding.progress.visibility =
//                            if(state.isLoading) View.VISIBLE else View.GONE
//                    }
//                }
//
//                launch{
//                    viewModel.uiEvent.collect{ e->
//                        when(e){
//                            is UiEvent.Toast ->
//                                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//        }
//    }
//}