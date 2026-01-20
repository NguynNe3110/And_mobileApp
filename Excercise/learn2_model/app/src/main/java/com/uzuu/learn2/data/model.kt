package com.uzuu.learn2.data

//thường thì sẽ khởi tạo mặc định như thế
//khuyên dùng 1 contructor (dgl default value)
data class model (
    val id: Int,
    val title: String? = "",
    val isLoading: Boolean = false,
    //val data: List<Model> = emptyList(),
    val content: String? = ""
)

// trưừng hợp bắt buộc cần nhiều contructor thì
//Model(1)
//Model(1, "Hello")
//Model(1, "Hello", "World")
//Kotlin sẽ tự sinh nhiều constructor ngầm

// =========================== cách 2 Secondary constructor (ít dùng) =====================
//Khi cần logic đặc biệt
data class Model2(
    val id: Int,
    val title: String,
    val content: String
) {
    constructor(id: Int) : this(id, "", "")

    constructor(id: Int, title: String) : this(id, title, "")
}

// =========================================================================================

// =========================== CÁCH 3: Factory function / companion object ====================
//Khi muốn đặt tên ý nghĩa
data class Model3(
    val id: Int,
    val title: String,
    val content: String
) {
    companion object {
        fun empty() = Model3(0, "", "")
        fun fromTitle(title: String) = Model3(0, title, "")
    }
}
//Model3.empty()
//Model3.fromTitle("Hello")

// =========================================================================================