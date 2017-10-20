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
import android.view.Gravity.*
import android.view.View
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
        toast(getString(R.string.network_error)); location.text = getString(R.string.located_failed)
    }

    override fun onSignSuccess(similarity: Float) {
        toast("相似度${similarity}")
        dialog?.dismiss()
        resetStatus()
    }

    override fun onSignError(error: Throwable) {
        if(error is SignException){

        }
        toast(error.message?:"未知异常")
        dialog?.dismiss()
        resetStatus()
    }

    fun resetStatus() {
        camera.visibility = View.INVISIBLE
        clockView.clockmode = true
    }


    override fun onSignStart() {
        dialog = indeterminateProgressDialog(resources.getString(R.string.signing))
    }


    private fun process(raw: ByteArray) {
        val bm = BitmapFactory.decodeByteArray(raw, 0, raw.size)
        val mat = Matrix()
        mat.setRotate(-90F)
        val bm2 = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, mat, true)
        mPresenter.faceCompare(bm2)
    }

    fun init() {
        initAnimation()

        val format = SimpleDateFormat("MM月dd号")
        date.text = format.format(Date())

        clockView.onTimeChanged { curTime -> time.text = curTime }
        relocate.setOnClickListener {
            location.text = "定位中..."
            mPresenter.startLocation()
        }

        clockView.setOnClickListener {

            camera.onFaceDetected { rawBit -> process(rawBit) }
            if (camera.visibility == View.INVISIBLE) {
                camera.visibility = View.VISIBLE
                camera.hasTake = false
            } else {
                camera.visibility = View.INVISIBLE
            }

            clockView.clockmode = !clockView.clockmode
        }


    }

    fun initAnimation() {
        window.enterTransition = Slide(START).setDuration(300)
//        window.exitTransition = Slide().setDuration(400)
    }


}
