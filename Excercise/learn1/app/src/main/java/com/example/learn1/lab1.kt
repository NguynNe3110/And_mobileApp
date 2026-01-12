package com.example.learn1

fun main () {
    println("heloo lesson 1")
    println("--------------")

    //khai bao va su dung bien
    // var and val
    var a : Int = 1
    var b : Int = 2
    var c = a + b
    println(c)

    var mess : String = "Tong 2 so $a va $b la: $c"

    println(mess)

    var sb : Float = 2.0f
    println(phepchia(a, sb))

    sb = 0f
    var kq = println(phepchia(a, sb))
    println("nếu k có kq thì in ra:" +kq)

    // khai bao va su dung mang
    var arrX = intArrayOf(1,2, 3,4)
    var arrY = arrayOf(1, 1.3, "nguyen")

    println("gia tri first X la: " +arrX[0])
    println("gia tri end Y la: " +arrY[2])

    // muon in nhieu dong
    println("""gia tri first X la: ${arrX[0]}
        |gia tri end Y la: ${arrY[2]}
    """.trimMargin())
}

fun phepchia (a : Int , b : Float) : String {
    if (b != 0f){
        val thuong = a / b
        return "ket qua la: $thuong"
    } else {
        return "b phai khac 0"
    }
}
class lab1 {
}