package com.jiangdg.demo.network

class ApiCallResponse (
    val userId: Int?,
    val id: Int?,
    val title: String?,
    val body: String?
) {
    fun flatten(): List<Item> {
        val flatpack = arrayListOf<Item>()
        userId?.let{flatpack.add(Item("userId", userId.toString(), TYPE_ITEM))}
        title?.let {
            flatpack.add(Item("title", title, TYPE_ITEM))
        }
        body?.let {
            flatpack.add(Item("body", body, TYPE_ITEM))
        }
        return flatpack
    }
}