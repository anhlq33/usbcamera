package com.jiangdg.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListDetailAdapter(context: Context, private val items: List<DetailItems>) :
    ArrayAdapter<DetailItems>(context, R.layout.item_detail, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.item_detail, parent, false)

        val textStt = view.findViewById<TextView>(R.id.labelTxt)
        textStt.text = items[position].detailName
        return view
    }
}