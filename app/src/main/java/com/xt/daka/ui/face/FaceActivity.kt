//package com.xt.daka.ui
//
//import android.annotation.SuppressLint
//import android.content.res.Configuration
//import android.graphics.*
//import android.hardware.camera2.*
//import android.media.ImageReader
//import android.os.Bundle
//import android.util.Log
//import android.util.Size
//import android.view.Surface
//import android.view.TextureView
//import com.xt.daka.util.sensor.TrackerWrapper.StableTracker
//import com.xt.daka.R
//import com.xt.daka.base.BaseActivity
//import kotlinx.android.synthetic.main.activity_face.*
//import org.greenrobot.eventbus.EventBus
//import org.jetbrains.anko.cameraManager
//import org.jetbrains.anko.toast
//import java.util.*
//
///**
// * Created by steve on 20-9-21.
// */
//class FaceActivity : BaseActivity(), FaceContract.View {
//
//    private var mCameraId: String? = null
//    private lateinit var mPreviewSize: Size
//    private val MAX_PREVIEW_WIDTH = 1920
//    private val MAX_PREVIEW_HEIGHT = 1080
//    lateinit var mImageReader: ImageReader
//    lateinit var mCameraDevice: CameraDevice
//    lateinit var mPreviewRequestBuilder: CaptureRequest.Builder
//    private var mFlashSupported: Boolean = false
//    lateinit private var mCaptureSession: CameraCaptureSession
//    private lateinit var stableTracker: StableTracker
//
//
//    val max = { lhs: Size, rhs: Size -> lhs.width * lhs.height - rhs.width * rhs.height }
//
//    val mDefaultCameraStateCallback = object : CameraDevice.StateCallback() {
//        override fun onOpened(cameraDevice: CameraDevice?) {
//            mCameraDevice = cameraDevice!!
//            createCaptureSession()
//        }
//
//        override fun onDisconnected(cameraDevice: CameraDevice?) {
//            cameraDevice!!.close()
//        }
//
//        override fun onError(p0: CameraDevice?, p1: Int) {
//
//        }
//    }
//    val mDefaultCaptureStateCallback = object : CameraCaptureSession.StateCallback() {
//        override fun onConfigureFailed(p0: CameraCaptureSession?) {
//            toast("config failed")
//        }
//
//        override fun onConfigured(session: CameraCaptureSession?) {
//
//            session!!.setRepeatingRequest(mPreviewRequestBuilder.build(), mDefaultCaptureCallback, null)
//        }
//
//    }
//    val mOnImageAvailableListener = ImageReader.OnImageAvailableListener { reader ->
//
//        val image = reader!!.acquireNextImage()
////        if(allowTake) {
////
////            if (stable.isStable) {
////                val buffer = image.planes[0].buffer
////                val data = ByteArray(buffer.remaining())
////                buffer.get(data)
////                list.add(data)
////                if(list.size == 1){
////                    allowTake  = false
////                    stable.unregister()
////                    EventBus.getDefault().post(Mgs(list))
////                }
////            }
////
////
////        }
//        image.close()
//
//    }
//    val mDefaultCaptureCallback = object : CameraCaptureSession.CaptureCallback() {
//
//        override fun onCaptureCompleted(session: CameraCaptureSession?, request: CaptureRequest?, result: TotalCaptureResult?) {
//            super.onCaptureCompleted(session, request, result)
//
//        }
//
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_face)
//
//
//        cameraView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
//            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {
//            }
//
//            override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {
//            }
//
//            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
//                return true
//            }
//
//            override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, p1: Int, p2: Int) {
//                openCamera("1", cameraView.width, cameraView.height)
//            }
//
//        }
//
//        stableTracker = StableTracker(this)
//
//        stableTracker.register()
//
//        stableTracker.addStateChangedListnere {
//            onNotStable { toast("hellow World") }
//        }
//
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this)
//        stableTracker.unregister()
//    }
//
//    override fun setPresenter(presenter: FaceContract.Presenter) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onSuccess() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onError() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    private fun createCaptureSession() {
//        val texture = cameraView.surfaceTexture
//        val surface = Surface(texture)
//        //预览请求builder
//        mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
//        mPreviewRequestBuilder.addTarget(surface)
//        mPreviewRequestBuilder.addTarget(mImageReader.surface)
//        mCameraDevice.createCaptureSession(arrayListOf(surface, mImageReader.surface), mDefaultCaptureStateCallback, null)
//
//    }
//
//    private fun setUpCameraOutputs(width: Int, height: Int) {
//
//        for (id in cameraManager.cameraIdList) {
//            val characteristics = cameraManager.getCameraCharacteristics(id)
//            val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
//            if (facing != null && facing == CameraCharacteristics.LENS_FACING_BACK) {
//                continue
//            }
//
//            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?: continue
//
//            val largest = Collections.max(map.getOutputSizes(ImageFormat.JPEG).asList(), max)
//
//            mImageReader = ImageReader.newInstance(largest.width, largest.height, ImageFormat.JPEG, 2)
//            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, null)
//
//            val displayRotation = windowManager.defaultDisplay.rotation
//            //noinspection ConstantConditions
//            val mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
//            var swappedDimensions = false
//            when (displayRotation) {
//                Surface.ROTATION_0, Surface.ROTATION_180 -> if (mSensorOrientation == 90 || mSensorOrientation == 270) {
//                    swappedDimensions = true
//                }
//                Surface.ROTATION_90, Surface.ROTATION_270 -> if (mSensorOrientation == 0 || mSensorOrientation == 180) {
//                    swappedDimensions = true
//                }
//                else -> Log.e("TAG", "Display rotation is invalid: " + displayRotation)
//            }
//
//            val displaySize = Point()
//            windowManager.defaultDisplay.getSize(displaySize)
//            var rotatedPreviewWidth = width
//            var rotatedPreviewHeight = height
//            var maxPreviewWidth = displaySize.x
//            var maxPreviewHeight = displaySize.y
//
//            if (swappedDimensions) {
//                rotatedPreviewWidth = height
//                rotatedPreviewHeight = width
//                maxPreviewWidth = displaySize.y
//                maxPreviewHeight = displaySize.x
//            }
//
//            if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
//                maxPreviewWidth = MAX_PREVIEW_WIDTH
//            }
//
//            if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
//                maxPreviewHeight = MAX_PREVIEW_HEIGHT
//            }
//
//            // Danger, W.R.! Attempting to use too large a preview size could  exceed the camera
//            // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
//            // garbage capture data.
//            mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture::class.java),
//                    rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
//                    maxPreviewHeight, largest)
//
//            // We fit the aspect ratio of TextureView to the size of preview we picked.
//            val orientation = resources.configuration.orientation
//            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                cameraView.setAspectRatio(
//                        mPreviewSize.width, mPreviewSize.height)
//            } else {
//                cameraView.setAspectRatio(
//                        mPreviewSize.height, mPreviewSize.width)
//            }
//
//            // Check if the flash is supported.
//            val available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
//            mFlashSupported = available == true
//
//            mCameraId = id
//            return
//
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun openCamera(cameraId: String, width: Int, height: Int) {
//        setUpCameraOutputs(width, height)
//        configureTransform(width, height)
//
//        cameraManager.openCamera(cameraId, mDefaultCameraStateCallback, null)
//
//
//    }
//
//    private fun configureTransform(viewWidth: Int, viewHeight: Int) {
//
//        val rotation = windowManager.defaultDisplay.rotation
//        val matrix = Matrix()
//        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
//        val bufferRect = RectF(0f, 0f, mPreviewSize.height.toFloat(), mPreviewSize.width.toFloat())
//        val centerX = viewRect.centerX()
//        val centerY = viewRect.centerY()
//        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
//            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
//            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
//            val scale = Math.max(
//                    viewHeight.toFloat() / mPreviewSize.height,
//                    viewWidth.toFloat() / mPreviewSize.width)
//            matrix.postScale(scale, scale, centerX, centerY)
//            matrix.postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
//        } else if (Surface.ROTATION_180 == rotation) {
//            matrix.postRotate(180f, centerX, centerY)
//        }
//
//        cameraView.setTransform(matrix)
//    }
//
//    private fun chooseOptimalSize(choices: Array<Size>, textureViewWidth: Int,
//                                  textureViewHeight: Int, maxWidth: Int, maxHeight: Int,
//                                  aspectRatio: Size): Size {
//
//        // Collect the supported resolutions that are at least as big as the preview Surface
//        val bigEnough = ArrayList<Size>()
//        // Collect the supported resolutions that are smaller than the preview Surface
//        val notBigEnough = ArrayList<Size>()
//        val w = aspectRatio.width
//        val h = aspectRatio.height
//        for (option in choices) {
//            if (option.width <= maxWidth && option.height <= maxHeight &&
//                    option.height == option.width * h / w) {
//                if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
//                    bigEnough.add(option)
//                } else {
//                    notBigEnough.add(option)
//                }
//            }
//        }
//
//        // Pick the smallest of those big enough. If there is no one big enough, pick the
//        // lar gest of those not big enough.
//        if (bigEnough.size > 0) {
//            return Collections.min(bigEnough, max)
//        } else if (notBigEnough.size > 0) {
//            return Collections.max(notBigEnough, max)
//        } else {
//            return choices[0]
//        }
//    }
//
//    private fun unlockFocus() {
//        // Reset the auto-focus trigger
//        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
//                CameraMetadata.CONTROL_AF_TRIGGER_CANCEL)
//        mCaptureSession.capture(mPreviewRequestBuilder.build(), mDefaultCaptureCallback, null)
//        // After this, the camera will go back to the normal state of preview.
//        mCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(), mDefaultCaptureCallback,
//                null)
//
//    }
//
//
//}