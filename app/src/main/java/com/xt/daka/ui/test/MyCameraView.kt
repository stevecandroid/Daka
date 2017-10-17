package com.xt.daka.ui.test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.android.synthetic.main.activity_camera1.*
import android.R.attr.data
import android.graphics.YuvImage
import com.xt.daka.R.id.camera
import java.io.ByteArrayOutputStream


/**
 * Created by steve on 17-10-14.
 */
class MyCameraView : SurfaceView , SurfaceHolder.Callback,Camera.PreviewCallback,Camera.FaceDetectionListener{


//    private var mTextureBuffer : IntArray?  =null
//    private var mBitmap : Bitmap? = null
    private lateinit var mCamera : Camera
//    private val mSurfaceTexture : SurfaceTexture
//    private val TEXTURE_ID = 10
//    private var previewHeight = 0
//    private var previewWidth = 0
//    private var mRect: Rect? = null
    var sizes : List<Camera.Size>? = null

    constructor( ctx : Context,  attrs : AttributeSet?) : super(ctx,attrs){
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this)
//        mSurfaceTexture = SurfaceTexture(TEXTURE_ID)

    }

    constructor(ctx : Context):this(ctx,null){
    }

    override fun surfaceChanged(p0: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.e("MyCameraView","CHANGE ")
//
//        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
//        mTextureBuffer = IntArray(width*height)
//        mRect = Rect(0,0,width,height)

//        val param = mCamera.parameters
//        val preSize = param.getSupportedPreviewSizes();
//        previewWidth = preSize.get(0).width;
//        previewHeight = preSize.get(0).height;



//        mCamera.setPreviewTexture(mSurfaceTexture)

        val sizes = mCamera.parameters.supportedPreviewSizes

        val size = getOptimalSzie(sizes)

        val supportWith = size.height
        val supportHeight : Int = size.width
        val radio : Float  = width.toFloat()/supportWith
        setMeasuredDimension(width,(supportHeight*radio).toInt())
        layout(0, (-(supportHeight*radio)/5).toInt(),width, ((supportHeight*radio).toInt()-(supportHeight*radio)/5).toInt())


    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        Log.e("MyCameraView","DESTORY")
        holder.removeCallback(this)
        mCamera.setPreviewCallback(null)
        mCamera.stopFaceDetection()
        mCamera.stopPreview()
        mCamera.lock()
        mCamera.release()
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {

        Log.e("MyCameraView","OPEN")

        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
        mCamera.setDisplayOrientation(90)
//        mCamera.setPreviewTexture(mSurfaceTexture)
        mCamera.setPreviewCallback(this)

        mCamera.setPreviewDisplay(holder)

        mCamera.setFaceDetectionListener(this)

        mCamera.startPreview();

        mCamera.startFaceDetection()

//        Log.e("MyCameraView","${pos[0]} and ${pos[1]}")
//        layout(-pos[0]+supportWith/2,-pos[1 ]+supportHeight/2,supportWith,supportHeight)



//        val colorEffects = param.supportedColorEffects
//        val colorItor = colorEffects.iterator()
//        while (colorItor.hasNext()) {
//            val currColor = colorItor.next()
//            if (currColor == Camera.Parameters.EFFECT_SOLARIZE) {
//                param.colorEffect = Camera.Parameters.EFFECT_SOLARIZE
//                break
//            }
//        }

    }

    private fun getOptimalSzie(sizes: List<Camera.Size>): Camera.Size {
        var pos = 0
        var ratio = 0
        var viewRatio = height/width
        sizes.forEachIndexed { index, size ->
            val curRatio = size.width/size.height
            if(ratio == 0 ) ratio = curRatio
            else if( (viewRatio - curRatio) < (viewRatio - ratio)  ){
                ratio = curRatio
                pos = index
            }
        }
        return sizes[pos]
    }

    override fun onPreviewFrame(data: ByteArray?, camera: Camera) {

//        if(mBitmap!= null) {
//            data?.forEachIndexed { index, byte -> mTextureBuffer!![index] = (0xff000000 or  byte.toLong() ).toInt() }
//            mBitmap?.setPixels(mTextureBuffer, 0, previewWidth, 0, 0, previewWidth, previewHeight)
//            synchronized(holder){
//                val canvas = holder.lockCanvas()
//                canvas.drawBitmap(mBitmap,null,mRect,null)
//                holder.unlockCanvasAndPost(canvas)
//            }
//        }
//        Log.e("MyCameraView",data?.toString())

        if(allowTake) {

            val parameters = camera.getParameters()
            val width = parameters.previewSize.width
            val height = parameters.previewSize.height

            val yuv = YuvImage(data, parameters.previewFormat, width, height, null)

            val out = ByteArrayOutputStream()
            yuv.compressToJpeg(Rect(0, 0, width, height), 50, out)
            val bytes = out.toByteArray()
            val b = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            detected?.invoke(bytes)
            allowTake = false
            hasTake = true

        }


    }
    var hasTake = false
    private var allowTake = false
    override fun onFaceDetection(faces: Array<out Camera.Face>?, camera: Camera?) {
        if(!hasTake) {
            allowTake = true
//            allowTake = false
//            mCamera.takePicture(null, null, object : Camera.PictureCallback {
//                override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
//                    if(detected != null){
//                        detected!!(data!! , faces )
//                    }
//                    mCamera.startPreview()
//                    mCamera.startFaceDetection()
//                }
//
//            })
        }
    }



    private var detected: ((bitmap : ByteArray) -> Unit)? = null

    fun onFaceDetected(detected : ( bitmap : ByteArray )->Unit){
        this.detected = detected
    }

}