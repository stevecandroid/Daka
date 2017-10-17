package com.xt.daka.ui.test

import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import com.xt.daka.R
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ImageUtils
import com.data.xt.daka.UtilActivity
import kotlinx.android.synthetic.main.activity_camera1.*
import com.xt.daka.R.id.camera
import com.xt.daka.network.youtu.Youtu
import com.xt.daka.util.io.createFile
import com.xt.daka.util.io.writeTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File


class Camera1 : UtilActivity() {
//    override fun surfaceChanged(holder: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
//
//    }
//
//    override fun surfaceDestroyed(p0: SurfaceHolder?) {
//        camera.stopFaceDetection()
//        camera.stopPreview();
//        camera.release();
//    }
//
//    override fun surfaceCreated(p0: SurfaceHolder?) {
//        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
//        val param = camera.parameters
//
//        camera.setDisplayOrientation(90)
//
//        val colorEffects = param.supportedColorEffects
//        val colorItor = colorEffects.iterator()
//        while (colorItor.hasNext()) {
//            val currColor = colorItor.next()
//            if (currColor == Camera.Parameters.EFFECT_SOLARIZE) {
//                param.colorEffect = Camera.Parameters.EFFECT_SOLARIZE
//                break
//            }
//        }
//        //设置完成需要再次调用setParameter方法才能生效
//        camera.parameters = param
//
//        camera.setPreviewDisplay(surfaceView.holder)
//
//        camera.setFaceDetectionListener { arrayOfFaces, camera -> arrayOfFaces.forEach { e-> Log.e("Camera1","Score${e.rect}")  } }
//
//        camera.startPreview()
//
//        camera.startFaceDetection()
//
//    }

//    lateinit var camera: Camera //这个是hardare的Camera对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera1)

        camera.onFaceDetected{
            bm  -> run{
//            val file = File(Environment.getExternalStorageDirectory().path + "/result.jpg")
//            bm.writeTo(file)

//            var path : String
//                Youtu.compareWithoutCompress(file.path,file.path).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe {
//                    next -> Log.e("Camera1",next.body().similarity.toString())
//                }

            

//            if( faces != null && faces.size == 1) {
//                faces!!.forEach { face -> Log.e("TAG", "${face.rect.centerX()} ${face.rect.centerY()}") }
//                val rect = faces[0].rect
//                val rawl = -rect.left + 1000
//                val rawt = -rect.top + 1000
//                val rawr = -rect.right +1000
//                val rawb = -rect.bottom + 1000
//
//                val ratioh = bm.height / 2000f
//                val ratiov = bm.width / 2000f
//                
//
//                val curl = rawl * ratioh
//                val curt = rawt * ratiov
//
//                val centerX = (-rect.centerY() + 1000) * ratiov
//                val centerY = (-rect.centerX() + 1000) * ratioh
//
//                val width = rect.width() * ratioh
//                val heigth = rect.height() * ratiov
//
////                Log.e("Camera1","bitmap ${bm.height}  ${bm.width}")
////                Log.e("Camera1","${(centerX - width).toInt()} , ${(centerY - heigth).toInt()} , ")
//                
//                val startX : Int=  if((centerY - heigth) < 0 ) 0 else (centerY-heigth ).toInt()
//                val startY : Int= if((centerX - width) < 0) 0 else (centerX -width).toInt()
//                
//                Log.e("Camera1","${width} and ${bm.height}")
////                
//                val ct = ImageUtils.clip(bm, startX , startY,
//                        heigth.toInt(),width.toInt())
//                
//                runOnUiThread {
//                    image.setImageBitmap(ct)
//                }
            }

        }
        }
//
//        surfaceView.holder.addCallback(this)
//        surfaceView.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    
}


