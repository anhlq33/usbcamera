package com.jiangdg.demo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.jiangdg.ausbc.MultiCameraClient
import com.jiangdg.ausbc.base.CameraFragment
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.ausbc.callback.ICaptureCallBack
import com.jiangdg.ausbc.camera.CameraUVC
import com.jiangdg.ausbc.render.effect.EffectBlackWhite
import com.jiangdg.ausbc.render.effect.EffectSoul
import com.jiangdg.ausbc.render.effect.EffectZoom
import com.jiangdg.ausbc.render.effect.bean.CameraEffect
import com.jiangdg.ausbc.utils.Logger
import com.jiangdg.ausbc.utils.MediaUtils
import com.jiangdg.ausbc.utils.ToastUtils
import com.jiangdg.ausbc.utils.Utils
import com.jiangdg.ausbc.utils.bus.BusKey
import com.jiangdg.ausbc.utils.bus.EventBus
import com.jiangdg.ausbc.widget.AspectRatioTextureView
import com.jiangdg.ausbc.widget.CaptureMediaView
import com.jiangdg.ausbc.widget.IAspectRatio
import com.jiangdg.ausbc.widget.PreviewImageView
import com.jiangdg.demo.databinding.FragmentHomeBinding
import com.jiangdg.utils.MMKVUtils
import com.jiangdg.utils.imageloader.ILoader
import com.jiangdg.utils.imageloader.ImageLoaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class HomeFragment : CameraFragment(), View.OnClickListener, CaptureMediaView.OnViewClickListener {
//    private var mMoreMenu: PopupWindow? = null
//    private val mEffectDataList by lazy {
//        arrayListOf(
//            CameraEffect.NONE_FILTER,
//            CameraEffect(
//                EffectBlackWhite.ID,
//                "BlackWhite",
//                CameraEffect.CLASSIFY_ID_FILTER,
//                effect = EffectBlackWhite(requireActivity()),
//                coverResId = R.mipmap.filter0
//            ),
//            CameraEffect.NONE_ANIMATION,
//            CameraEffect(
//                EffectZoom.ID,
//                "Zoom",
//                CameraEffect.CLASSIFY_ID_ANIMATION,
//                effect = EffectZoom(requireActivity()),
//                coverResId = R.mipmap.filter2
//            ),
//            CameraEffect(
//                EffectSoul.ID,
//                "Soul",
//                CameraEffect.CLASSIFY_ID_ANIMATION,
//                effect = EffectSoul(requireActivity()),
//                coverResId = R.mipmap.filter1
//            ),
//        )
//    }
//
//    private lateinit var mViewBinding: FragmentHomeBinding
//
//    override fun initView() {
//        super.initView()
//        mViewBinding.resolutionBtn.setOnClickListener(this)
//        mViewBinding.albumPreviewIv.setOnClickListener(this)
//        mViewBinding.captureBtn.setOnViewClickListener(this)
//        mViewBinding.albumPreviewIv.setTheme(PreviewImageView.Theme.DARK)
//    }
//
//    override fun initData() {
//        super.initData()
//        EventBus.with<Int>(BusKey.KEY_FRAME_RATE).observe(this, {
//            mViewBinding.frameRateTv.text = "frame rate:  $it fps"
//        })
//
//        EventBus.with<Boolean>(BusKey.KEY_RENDER_READY).observe(this, { ready ->
//            if (! ready) return@observe
//            getDefaultEffect()?.apply {
//                when(getClassifyId()) {
//                    CameraEffect.CLASSIFY_ID_FILTER -> {
//                        // check if need to set anim
//                        val animId = MMKVUtils.getInt(KEY_ANIMATION, -99)
//                        if (animId != -99) {
//                            mEffectDataList.find {
//                                it.id == animId
//                            }?.also {
//                                if (it.effect != null) {
//                                    addRenderEffect(it.effect!!)
//                                }
//                            }
//                        }
//                        // set effect
//                        val filterId = MMKVUtils.getInt(KEY_FILTER, -99)
//                        if (filterId != -99) {
//                            removeRenderEffect(this)
//                            mEffectDataList.find {
//                                it.id == filterId
//                            }?.also {
//                                if (it.effect != null) {
//                                    addRenderEffect(it.effect!!)
//                                }
//                            }
//                            return@apply
//                        }
//                        MMKVUtils.set(KEY_FILTER, getId())
//                    }
//                    CameraEffect.CLASSIFY_ID_ANIMATION -> {
//                        // check if need to set filter
//                        val filterId = MMKVUtils.getInt(KEY_ANIMATION, -99)
//                        if (filterId != -99) {
//                            mEffectDataList.find {
//                                it.id == filterId
//                            }?.also {
//                                if (it.effect != null) {
//                                    addRenderEffect(it.effect!!)
//                                }
//                            }
//                        }
//                        // set anim
//                        val animId = MMKVUtils.getInt(KEY_ANIMATION, -99)
//                        if (animId != -99) {
//                            removeRenderEffect(this)
//                            mEffectDataList.find {
//                                it.id == animId
//                            }?.also {
//                                if (it.effect != null) {
//                                    addRenderEffect(it.effect!!)
//                                }
//                            }
//                            return@apply
//                        }
//                        MMKVUtils.set(KEY_ANIMATION, getId())
//                    }
//                    else -> throw IllegalStateException("Unsupported classify")
//                }
//            }
//        })
//    }
//
//    override fun onCameraState(
//        self: MultiCameraClient.ICamera,
//        code: ICameraStateCallBack.State,
//        msg: String?
//    ) {
//        when (code) {
//            ICameraStateCallBack.State.OPENED -> handleCameraOpened()
//            ICameraStateCallBack.State.CLOSED -> handleCameraClosed()
//            ICameraStateCallBack.State.ERROR -> handleCameraError(msg)
//        }
//    }
//
//    private fun handleCameraError(msg: String?) {
//        mViewBinding.uvcLogoIv.visibility = View.VISIBLE
//        mViewBinding.frameRateTv.visibility = View.GONE
//        ToastUtils.show("camera opened error: $msg")
//    }
//
//    private fun handleCameraClosed() {
//        mViewBinding.uvcLogoIv.visibility = View.VISIBLE
//        mViewBinding.frameRateTv.visibility = View.GONE
//        ToastUtils.show("camera closed success")
//    }
//
//    private fun handleCameraOpened() {
//        mViewBinding.uvcLogoIv.visibility = View.GONE
//        mViewBinding.frameRateTv.visibility = View.VISIBLE
//        mViewBinding.brightnessSb.max = (getCurrentCamera() as? CameraUVC)?.getBrightnessMax() ?: 100
//        mViewBinding.brightnessSb.progress = (getCurrentCamera() as? CameraUVC)?.getBrightness() ?: 0
//        Logger.i(TAG, "max = ${mViewBinding.brightnessSb.max}, progress = ${mViewBinding.brightnessSb.progress}")
//        mViewBinding.brightnessSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                (getCurrentCamera() as? CameraUVC)?.setBrightness(progress)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//
//            }
//        })
//        ToastUtils.show("camera opened success")
//    }
//
//    override fun getCameraView(): IAspectRatio {
//        return AspectRatioTextureView(requireContext())
//    }
//
//    override fun getCameraViewContainer(): ViewGroup {
//        return mViewBinding.cameraViewContainer
//    }
//
//    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
//        mViewBinding = FragmentHomeBinding.inflate(inflater, container, false)
//        return mViewBinding.root
//    }
//
//    override fun getGravity(): Int = Gravity.CENTER
//
//    override fun onViewClick() {
//        if (! isCameraOpened()) {
//            ToastUtils.show("camera not worked!")
//            return
//        }
//        captureImage()
//    }
//
//    private fun captureImage() {
//        captureImage(object : ICaptureCallBack {
//            override fun onBegin() {
//                mViewBinding.albumPreviewIv.showImageLoadProgress()
//                mViewBinding.albumPreviewIv.setNewImageFlag(true)
//            }
//
//            override fun onError(error: String?) {
//                ToastUtils.show(error ?: "未知异常")
//                mViewBinding.albumPreviewIv.cancelAnimation()
//                mViewBinding.albumPreviewIv.setNewImageFlag(false)
//            }
//
//            override fun onComplete(path: String?) {
//                showRecentMedia(true)
//                mViewBinding.albumPreviewIv.setNewImageFlag(false)
//            }
//        })
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//    }
//
//    override fun onClick(v: View?) {
//        clickAnimation(v!!, object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator?) {
//                when (v) {
//                    mViewBinding.resolutionBtn -> {
//                        showResolutionDialog()
//                    }
//                    mViewBinding.albumPreviewIv -> {
//                        goToGalley()
//                    }
//                    else -> {
//                    }
//                }
//            }
//        })
//    }
//
//    @SuppressLint("CheckResult")
//    private fun showResolutionDialog() {
//        mMoreMenu?.dismiss()
//        getAllPreviewSizes().let { previewSizes ->
//            if (previewSizes.isNullOrEmpty()) {
//                ToastUtils.show("Get camera preview size failed")
//                return
//            }
//            val list = arrayListOf<String>()
//            var selectedIndex: Int = -1
//            for (index in (0 until previewSizes.size)) {
//                val w = previewSizes[index].width
//                val h = previewSizes[index].height
//                getCurrentPreviewSize()?.apply {
//                    if (width == w && height == h) {
//                        selectedIndex = index
//                    }
//                }
//                list.add("$w x $h")
//            }
//            MaterialDialog(requireContext()).show {
//                listItemsSingleChoice(
//                    items = list,
//                    initialSelection = selectedIndex
//                ) { dialog, index, text ->
//                    if (selectedIndex == index) {
//                        return@listItemsSingleChoice
//                    }
//                    updateResolution(previewSizes[index].width, previewSizes[index].height)
//                }
//            }
//        }
//    }
//
//    private fun goToGalley() {
//        try {
//            Intent(
//                Intent.ACTION_VIEW,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            ).apply {
//                startActivity(this)
//            }
//        } catch (e: Exception) {
//            ToastUtils.show("open error: ${e.localizedMessage}")
//        }
//    }
//
//    private fun showRecentMedia(isImage: Boolean? = null) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            context ?: return@launch
//            if (! isFragmentAttached()) {
//                return@launch
//            }
//            try {
//                if (isImage != null) {
//                    MediaUtils.findRecentMedia(requireContext(), isImage)
//                } else {
//                    MediaUtils.findRecentMedia(requireContext())
//                }?.also { path ->
//                    val size = Utils.dp2px(requireContext(), 38F)
//                    ImageLoaders.of(this@HomeFragment)
//                        .loadAsBitmap(path, size, size, object : ILoader.OnLoadedResultListener {
//                            override fun onLoadedSuccess(bitmap: Bitmap?) {
//                                lifecycleScope.launch(Dispatchers.Main) {
//                                    mViewBinding.albumPreviewIv.canShowImageBorder = true
//                                    mViewBinding.albumPreviewIv.setImageBitmap(bitmap)
//                                }
//                            }
//
//                            override fun onLoadedFailed(error: Exception?) {
//                                lifecycleScope.launch(Dispatchers.Main) {
//                                    ToastUtils.show("Capture image error.${error?.localizedMessage}")
//                                    mViewBinding.albumPreviewIv.cancelAnimation()
//                                }
//                            }
//                        })
//                }
//            } catch (e: Exception) {
//                activity?.runOnUiThread {
//                    ToastUtils.show("${e.localizedMessage}")
//                }
//                Logger.e(TAG, "showRecentMedia failed", e)
//            }
//        }
//    }
//
//    private fun clickAnimation(v: View, listener: Animator.AnimatorListener) {
//        val scaleXAnim: ObjectAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 0.4f, 1.0f)
//        val scaleYAnim: ObjectAnimator = ObjectAnimator.ofFloat(v, "scaleY", 1.0f, 0.4f, 1.0f)
//        val alphaAnim: ObjectAnimator = ObjectAnimator.ofFloat(v, "alpha", 1.0f, 0.4f, 1.0f)
//        val animatorSet = AnimatorSet()
//        animatorSet.duration = 150
//        animatorSet.addListener(listener)
//        animatorSet.playTogether(scaleXAnim, scaleYAnim, alphaAnim)
//        animatorSet.start()
//    }
//
//    companion object {
//        private const val TAG  = "DemoFragment"
//        const val KEY_FILTER = "filter"
//        const val KEY_ANIMATION = "animation"
//    }
//}

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}