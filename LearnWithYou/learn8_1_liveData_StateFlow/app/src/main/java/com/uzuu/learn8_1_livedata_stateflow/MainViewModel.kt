package com.uzuu.learn8_1_livedata_stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class UiState(
    // vì là stateflow nên phải có giá trị ban đầu
    val isLoading: Boolean = false,
    val resultText: String = "Ket qua se hien o day",
    val isButtonEnabled: Boolean = true
)

// saled class là lớp tập hợp các lớp con
sealed class UiEvent{
//    Nó nói với compiler rằng:
//    “TẤT CẢ các class con của tao
//    PHẢI được khai báo trong CÙNG FILE này”
//
//    📌 Nghĩa là:
//    Không ai ở file khác tự ý tạo thêm UiEvent mới
//    Danh sách class con là CỐ ĐỊNH & BIẾT TRƯỚC
    data class Toast(val message: String) : UiEvent()
//    👉 Compiler biết chắc:
//    Result chỉ có 1 loại: Toast
    // mục đích để when k cần else, nếu quên 1 case nó sẽ báo lỗi

    // khi này class Toast quan hệ is-a với Uievent
    // giống kiểu đa hình và kế thừa
    // toast là 1 uievent
}
class MainViewModel : ViewModel(){
    // khởi tạo stateflow cho viewmodel và activity (or)
    private val _uistate = MutableStateFlow(UiState())
    val uiState = _uistate.asStateFlow()

    // khởi tạo sharedflow cho viewmodel và activity (or)

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    // hàm logic khi nhán nút
    fun onSearchClick(query : String){
        // nếu trống
        if(query.isBlank()){
            // bắn emit sang cho main
            // emit là 1 hàm suspend fun nên phải được bọc trong coroutine
            //coroutine được scope của phạm vi tạo ra
            viewModelScope.launch{
                _event.emit(UiEvent.Toast("Ban chua nhap gi!"))
            }
        }
        // cập nhật uistate
        viewModelScope.launch{
            _uistate.value = _uistate.value.copy(
                isLoading = true,
                resultText = "finding: $query ...",
                isButtonEnabled = false
            )
// sau 1 s
            delay(1000)
// cập nhật state
            _uistate.value = _uistate.value.copy(
                isButtonEnabled = true,
                isLoading = false,
                resultText = "Ket qua cua $query la: ${query.reversed()}"
            )
// emit phát tín hiệu
            _event.emit(UiEvent.Toast("Tim thanh cong!"))
        }
    }
}

//data class UiState(
//    val isLoading: Boolean = false,
//    val isButtonEnabled: Boolean = true,
//    val resultText: String = "Ket qua se hien o day!"
//)
//
//sealed class UiEvent{
//    data class Toast(var message : String) : UiEvent()
//}
//
//class MainViewModel : ViewModel(){
//    private val _uiState = MutableStateFlow(UiState())
//    val uiState = _uiState.asStateFlow()
//
//    private val _uiEvent = MutableSharedFlow<UiEvent>()
//    val uiEvent = _uiEvent.asSharedFlow()
//
//    fun onSearchClick(query : String){
//        if(query.isBlank()){
//            viewModelScope.launch{
//                _uiEvent.emit(UiEvent.Toast("Ban chua nhap gi!"))
//            }
//            return;
//        }
//
//        viewModelScope.launch{
//            _uiState.value = _uiState.value.copy(
//                isButtonEnabled = false,
//                resultText = "Finding $query ...",
//                isLoading = true
//            )
//
//            delay(1000)
//
//            _uiState.value = _uiState.value.copy(
//                isButtonEnabled = true,
//                resultText = "Ket qua cua $query la ${query.reversed()}",
//                isLoading = false
//            )
//
//            _uiEvent.emit(UiEvent.Toast("Find success!"))
//        }
//    }
//}