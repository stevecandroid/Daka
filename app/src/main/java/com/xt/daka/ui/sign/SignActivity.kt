package com.xt.daka.ui.sign

import android.app.ProgressDialog
import android.os.Bundle
import android.transition.Slide
import com.xt.daka.R
import com.xt.daka.base.BaseActivity
import com.xt.daka.util.helper.toast
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.view.Gravity.*
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.ImageUtils
import com.xt.daka.DakaUser
import kotlinx.android.synthetic.main.activity_sign.*
import org.jetbrains.anko.indeterminateProgressDialog


class SignActivity : BaseActivity(), SignContract.View {

    lateinit var mPresenter: SignContract.Presenter
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        mPresenter = SignPresenter(this)
        mPresenter.subscribe()
        init()

    }

    override fun onLocated(loca: String) {
        location.text = loca
    }

    override fun onLocatedError() {
        toast("定位失败")
    }

    override fun onSignSuccess() {
        toast("签到成功")
        dialog?.dismiss()
        resetStatus()
    }

    override fun onSignError(error: Throwable) {
        if(error is SignException){
            when(error.status){
                SignException.LOCAL_PHOTO_ERROR -> toast("照片模糊,请重新拍摄")
                SignException.REMOTE_PHOTO_ERROR -> toast("请联系管理员更换照片")
                SignException.SIMILARITY_TOO_SMALL -> toast("请确认是同一个人")
            }
        }else{
            toast(error.toString())
        }
        dialog?.dismiss()
        resetStatus()
    }

    fun resetStatus() {
        camera.visibility = View.INVISIBLE
        clockView.clockmode = true
    }


    override fun onSignStart() {
        dialog = ProgressDialog.show(this,null,"签到中..",true)
    }


    private fun process(raw: ByteArray) {
        val bm = BitmapFactory.decodeByteArray(raw, 0, raw.size)
        val mat = Matrix()
        mat.setRotate(-90F)
        val bm2 = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, mat, true)
        mPresenter.signIn(bm2)
    }

    fun init() {
        initAnimation()

        mPresenter.getFace()

        name.text = DakaUser.user?.name

        val format = SimpleDateFormat("MM月dd号")
        date.text = format.format(Date())

        clockView.onTimeChanged { curTime -> time.text = curTime }
        relocate.setOnClickListener {
            location.text = "定位中..."
            mPresenter.startLocation()
        }

        camera.onFaceDetected { rawBit -> process(rawBit) }

        clockView.setOnClickListener {

            if (camera.visibility == View.INVISIBLE) {
                camera.visibility = View.VISIBLE
                camera.hasTake = false
            } else {
                camera.visibility = View.INVISIBLE
            }

            clockView.clockmode = !clockView.clockmode
        }

        take.setOnClickListener {
            camera.takePhotoAnyWay()
        }

    }

    override fun getFace(bitmap: Bitmap) {
        head.setImageBitmap(bitmap)
    }


    fun initAnimation() {
        window.enterTransition = Slide(START).setDuration(300)
//        window.exitTransition = Slide().setDuration(400)
    }


}
