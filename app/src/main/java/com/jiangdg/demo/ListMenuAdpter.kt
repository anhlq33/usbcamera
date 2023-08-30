package com.jiangdg.demo

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView

class ListMenuAdpter(context: Context, private val items: List<MenuItems>) :
    ArrayAdapter<MenuItems>(context, R.layout.item_menu, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(R.layout.item_menu, parent, false)
        view.findViewById<TextView>(R.id.titleTxtId).text = items[position].title
        view.findViewById<TextView>(R.id.noteTxtId).text = items[position].note
        if(items[position].isSwt == false) {
            view.findViewById<TextView>(R.id.switcher).visibility = Switch.GONE
            view.findViewById<RelativeLayout>(R.id.menuTagId).setOnClickListener{
                lateinit var listView: ListView
                val dialogView = inflater.inflate(R.layout.list_detail, null)
                // Tạo builder cho Dialog
                val dialogBuilder = AlertDialog.Builder(context)
                // Thiết lập layout cho Dialog
//                        val dialogView = layoutInflater.inflate(R.layout.sdt_sos, null)
                dialogBuilder.setView(dialogView)

                val cancelpress = dialogView.findViewById<TextView>(R.id.cancelTxt)
                val applypress = dialogView.findViewById<TextView>(R.id.applyTxt)
                // Tạo và hiển thị Dialog
                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                listView = dialogView.findViewById<ListView>(R.id.listView)
//                        val items = listOf("SDT 1", "SDT 2", "SDT 3", "SDT 4", "SDT 5")
                val adapter = ListDetailAdapter(dialogView.context,items[position].detailTitle)
                listView.adapter = adapter

                // Xử lý sự kiện khi nút Đóng được nhấn
                cancelpress.setOnClickListener {
                    alertDialog.dismiss() // Đóng Dialog
                }

                applypress.setOnClickListener {
                    alertDialog.dismiss() // Đóng Dialog
                }
            }
        }
//        else {
//            view.findViewById<RelativeLayout>(R.id.menuTagId).setOnClickListener{
//                view.findViewById<Switch>(R.id.switcher).setChecked
//            }
//        }
        return view
    }
}