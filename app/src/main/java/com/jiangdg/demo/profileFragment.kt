package com.jiangdg.demo

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [profileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class profileFragment : Fragment() {
    private lateinit var viewProfile: View
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewProfile = inflater.inflate(R.layout.fragment_profile, container, false)
//        val viewsdt = viewProfile.findViewById<TextView>(R.id.sdtsostxt)
//        viewsdt.setOnClickListener {
//            showDialogSdt(inflater)
//        }

        val itemMenu = listOf(MenuItems(MenuIndex.LOA,"Loa", "Bật tắt loa", true, listOf(DetailItems("", false))),
                              MenuItems(MenuIndex.RFID, "Thẻ RFID", "Có sử dụng thẻ RFID không?", true, listOf(DetailItems("", false))),
                              MenuItems(MenuIndex.SDT,"Số điện thoại khẩn cấp", "Danh sách các số điện thoại để thông báo khi có cháy", false,
                                    listOf(DetailItems("Số điện thoại 1", true), DetailItems("Số điện thoại 2", true),
                                        DetailItems("Số điện thoại 3", true), DetailItems("Số điện thoại 4", true), DetailItems("Số điện thoại 5", true))),
                              MenuItems(MenuIndex.THONGTIN1,"Thông tin thiết bị 1", "số IMEI, ID, APN, Cell ID, Version FW, Ngày cập nhật FW", false,
                                    listOf(DetailItems("Số IMEI", true), DetailItems("ID", true),
                                        DetailItems("APN", false), DetailItems("Cell ID", false),
                                        DetailItems("Version FW", false), DetailItems("Ngày cập nhật FW", false))),
                              MenuItems(MenuIndex.THONGTIN2,"Thông tin thiết bị 2", "chu kỳ gửi tín hiệu về server, input 1-8 điện áp, bảng trạng thái hiển thị led", false,
                                    listOf(DetailItems("Chu kỳ gửi tín hiệu về server", true), DetailItems("Input 8 điện áp", true),
                                        DetailItems("Bảng trạng thái hiển thị led", true), )))

        val adapter = ListMenuAdpter(viewProfile.context,itemMenu)
        listView = viewProfile.findViewById(R.id.listViewMenu)
        listView.adapter = adapter

        // Inflate the layout for this fragment
        return viewProfile
    }
//
//    private fun showDialogSdt(inflater: LayoutInflater) {
//        val popupsdt = inflater.inflate(R.layout.sdt_sos, null)
//        // Tạo builder cho Dialog
//        val dialogBuilder = AlertDialog.Builder(viewProfile.context)
//        // Thiết lập layout cho Dialog
//        val dialogView = layoutInflater.inflate(R.layout.sdt_sos, null)
//        dialogBuilder.setView(dialogView)
//
//        val cancelpress = dialogView.findViewById<TextView>(R.id.cancelTxt)
//        val applypress = dialogView.findViewById<TextView>(R.id.applyTxt)
//        // Tạo và hiển thị Dialog
//        val alertDialog = dialogBuilder.create()
//        alertDialog.show()
//
//        listView = dialogView.findViewById(R.id.listView)
//        val items = listOf("SDT 1", "SDT 2", "SDT 3", "SDT 4", "SDT 5")
//        val adapter = ListSdtAdapter(popupsdt.context,items)
//        listView.adapter = adapter
//
//        // Xử lý sự kiện khi nút Đóng được nhấn
//        cancelpress.setOnClickListener {
//            alertDialog.dismiss() // Đóng Dialog
//        }
//
//        applypress.setOnClickListener {
//            alertDialog.dismiss() // Đóng Dialog
//        }
//    }

}