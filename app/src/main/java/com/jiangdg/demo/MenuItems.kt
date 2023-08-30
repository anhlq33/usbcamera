package com.jiangdg.demo

enum class MenuIndex {
    LOA, RFID, SDT, THONGTIN1, THONGTIN2
}
data class DetailItems(val detailName: String, val isNumber: Boolean)
data class MenuItems(val index: MenuIndex, val title: String, val note: String, val isSwt: Boolean, val detailTitle: List<DetailItems>)

