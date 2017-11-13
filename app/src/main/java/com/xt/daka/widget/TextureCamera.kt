package com.xt.daka.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import android.graphics.YuvImage
import android.view.TextureView
import java.io.ByteArrayOutputStream


/**
 * Created by steve on 17-10-14.
 */
class TextureCamera : TextureView , TextureView.SurfaceTextureListener,Camera.PreviewCallback,Camera.FaceDetectionListener{

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        Log.e("TextureCamera","CHANGE")

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {

    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        mCamera.setPreviewCallback(null)
        mCamera.stopPreview()
        mCamera.lock()
        mCamera.release()
        Log.e("TextureCamera","DESTORY")
        return true


    
    }

    var supportFaceDetecte = false

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {


        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
        mCamera.setDisplayOrientation(90)

        val sizes = mCamera.parameters.supportedPreviewSizes
        val size = getOptimalSzie(sizes)
        val supportWith = size.height
        val supportHeight : Int = size.width
        val radio : Float  = width.toFloat()/supportWith
        setMeasuredDimension(width,(supportHeight*radio).toInt())
        layout(0, (-(supportHeight*radio)/5).toInt(),width, ((supportHeight*radio).toInt()-(supportHeight*radio)/5).toInt())


        if(mCamera.parameters.maxNumDetectedFaces > 0 ) supportFaceDetecte = true

        mCamera.setPreviewCallback(this)

        mCamera.setPreviewTexture(surfaceTexture)

        if(supportFaceDetecte)
        mCamera.setFaceDetectionListener(this)

        mCamera.startPreview();

        if(supportFaceDetecte)
        mCamera.startFaceDetection()

    }


    private lateinit var mCamera : Camera
    
    constructor( ctx : Context,  attrs : AttributeSet?) : super(ctx,attrs){
        surfaceTextureListener = this
    }

    constructor(ctx : Context):this(ctx,null){
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

        if(allowTake) {

            allowTake = false
            hasTake = true
            
            Log.e("TextureCamera","TAKE")

            val parameters = camera.getParameters()
            val width = parameters.previewSize.width
            val height = parameters.previewSize.height

            val yuv = YuvImage(data, parameters.previewFormat, width, height, null)

            val out = ByteArrayOutputStream()
            yuv.compressToJpeg(Rect(0, 0, width, height), 50, out)
            val bytes = out.toByteArray()
            detected?.invoke(bytes)

        }


    }

    var hasTake = false
    private var allowTake = false
    override fun onFaceDetection(faces: Array<out Camera.Face>?, camera: Camera?) {
        takePhotoAnyWay()
    }

    fun takePhotoAnyWay(){
        if(!hasTake) {
            allowTake = true
        }
    }



    private var detected: ((bitmap : ByteArray) -> Unit)? = null

    fun onFaceDetected(detected : ( bitmap : ByteArray )->Unit){
        this.detected = detected
    }

}