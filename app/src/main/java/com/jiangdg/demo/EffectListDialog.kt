/*
 * Copyright 2017-2022 Jiangdg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jiangdg.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jiangdg.ausbc.base.BaseDialog
import com.jiangdg.ausbc.render.effect.bean.CameraEffect
import com.jiangdg.utils.MMKVUtils
import com.jiangdg.ausbc.utils.Utils
import com.jiangdg.utils.imageloader.ImageLoaders

/** Effect list dialog
 *
 * @author Created by jiangdg on 2022/2/8
 */
@SuppressLint("NotifyDataSetChanged")
class EffectListDialog(activity: Activity) : BaseDialog(activity, portraitWidthRatio = 1f),
    View.OnClickListener {
    private var mListener: OnEffectClickListener? = null
    private var mEffectList: ArrayList<CameraEffect> = ArrayList()
    private val mEffectMap = HashMap<Int, List<CameraEffect>>()

    init {
        mDialog.window?.let {
            it.setGravity(Gravity.BOTTOM)
            it.setWindowAnimations(R.style.camera2_anim_down_to_top)

            it.attributes?.run {
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = (200f / 360f * Utils.getScreenWidth(activity)).toInt()
                mDialog.window?.attributes = this
            }
        }

        mDialog.window?.setDimAmount(0f)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }

    fun setData(list: List<CameraEffect>, listener: OnEffectClickListener) {
        mListener = listener
        mEffectList.clear()
        mEffectList.addAll(list)
        initEffectData()
        initEffectTabs()
    }

    private fun getCurFilterId() = MMKVUtils.getInt(KEY_FILTER, CameraEffect.ID_NONE_FILTER)

    private fun getCurAnimationId() = MMKVUtils.getInt(KEY_ANIMATION, CameraEffect.ID_NONE_ANIMATION)

    private fun initEffectTabs() {
        getCurAnimationId().let { curAnimId ->
            if (curAnimId != CameraEffect.ID_NONE_ANIMATION) {
                return
            }
        }
        val curFilterId = getCurFilterId()
    }

    private fun initEffectData() {
        // filter list
        mEffectList.filter {
            it.classifyId == CameraEffect.CLASSIFY_ID_FILTER
        }.let {
            val list = ArrayList<CameraEffect>().apply {
                addAll(it)
            }
            mEffectMap[CameraEffect.CLASSIFY_ID_FILTER] = list
        }
        // animation list
        mEffectList.filter {
            it.classifyId == CameraEffect.CLASSIFY_ID_ANIMATION
        }.let {
            val list = ArrayList<CameraEffect>().apply {
                addAll(it)
            }
            mEffectMap[CameraEffect.CLASSIFY_ID_ANIMATION] = list
        }
    }

    override fun onClick(v: View?) {
    }

    interface OnEffectClickListener {
        fun onEffectClick(effect: CameraEffect)
    }

    companion object {
        const val KEY_FILTER = "filter"
        const val KEY_ANIMATION = "animation"
    }
}
