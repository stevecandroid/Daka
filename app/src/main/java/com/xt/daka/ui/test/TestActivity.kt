//package com.xt.daka.ui.test
//
//import android.content.Context
//import android.content.pm.ActivityInfo
//import android.databinding.*
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Rect
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.LinearLayoutManager
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.RelativeLayout
//import android.widget.TextView
//import com.xt.daka.R
//import com.xt.daka.base.*
//
//
//import com.xt.daka.databinding.TestAcitivityBinding
//import com.xt.daka.databinding.TestItemBinding
//import com.xt.daka.ui.main.SignActivity
//import kotlinx.android.synthetic.main.test_acitivity.*
//import kotlinx.coroutines.experimental.async
//import org.jetbrains.anko.doAsync
//import org.jetbrains.anko.sdk25.coroutines.onClick
//import org.jetbrains.anko.sdk25.coroutines.onLongClick
//import org.opencv.android.*
//import org.opencv.core.Mat
//import org.opencv.imgproc.Imgproc
//import org.opencv.android.LoaderCallbackInterface
//import org.opencv.android.OpenCVLoader
//import android.opengl.ETC1.getHeight
//import android.opengl.ETC1.getWidth
//import org.opencv.core.Core
//import org.opencv.core.Point
//
//
///**
// * Created by steve on 17-9-25.
// */
//class TestActivity : BaseActivity(), TestContract.View  , CameraBridgeViewBase.CvCameraViewListener2{
//    override fun onCameraViewStopped() {
//    }
//
//    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat? {
//        mRgba = inputFrame!!.rgba();
////        val mRgbaT = mRgba!!.t();
////        Core.flip(mRgba!!.t(), mRgbaT, 1);
////        Imgproc.resize(mRgbaT, mRgbaT, mRgba!!.size());
//        return mRgba;
//
//    }
//
//    override fun onCameraViewStarted(width: Int, height: Int) {
//        mRgba = Mat()
//        mFlipRgba = Mat()
//
//    }
//
//
//    private var mRgba: Mat? = null
//    private var mFlipRgba: Mat? = null
//
//
//    lateinit var binding : TestAcitivityBinding
//    lateinit var mPresenter : TestContract.Presenter
//
//    override fun onDownLoadCompleted() {
//
//    }
//
//    override fun onUpdateCompleted(result: String) {
//        binding.textView.setText(result)
//    }
//
//
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.test_acitivity)
////        val binding =  TestAcitivityBinding.inflate(layoutInflater)
////        binding.textView.text = User("Steven").name
//////        val binding = TestAcitivityBinding.inflate(layoutInflater)
////
////        binding.user = User("Steven")
//
//        var user = User(ObservableField("FUCKER"))
//        binding = DataBindingUtil.setContentView<TestAcitivityBinding>(this, R.layout.test_acitivity)
//        binding.user = user
//        mPresenter = TestPresenter(this)
//        binding.presenter = mPresenter as TestPresenter
//
//
//
//
////        doAsync{
////           Thread.sleep(2000)
////            val mRgba = Mat()
////            var Bitmap : Bitmap? = null
////            var init : Mat? = null
////            albumPicker.selectedPicAndHandle { pic -> BitmapFactory.decodeFile(pic)}
////            Utils.bitmapToMat(Bitmap,init)
////            Imgproc.cvtColor(init, mRgba, Imgproc.COLOR_GRAY2RGBA, 4)
////            Utils.matToBitmap(mRgba,Bitmap)
////            runOnUiThread {  image.setImageBitmap(Bitmap) }
////        }
////        val a = ObservableArrayList
//
//
////        albumPicker.selectedPicAndHandle { pic -> srcBitmap = BitmapFactory.decodeFile(pic) ; image.setImageBitmap(srcBitmap)}
////
////        image.setOnClickListener {
////            image.setImageBitmap(procSrc2Gray())
////        }
//    }
//    lateinit var srcBitmap : Bitmap
//
//
//
//
//     val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
//         override fun onManagerConnected(status: Int) {
//             when (status) {
//                 LoaderCallbackInterface.SUCCESS -> {
//                 }
//                 else -> {
//                     super.onManagerConnected(status)
//                 }
//             }
//         }
//     }
//
//    override fun onResume() {
//        super.onResume()
//        Log.e("TestActivity","RESUME")
//        if (!OpenCVLoader.initDebug()) {
//            Log.e("TestActivity", "Internal OpenCV library not found. Using OpenCV Manager for initialization")
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback)
//        } else {
//            Log.e("TestActivity", "OpenCV library found inside package. Using it!")
//            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
//        }
//    }
//
//
//    class Adapter : BaseAdapter<User, UserView>() {
//
//        override fun createView(ctx: Context): UserView {
//
//            return UserView(ctx)
//
//        }
//
//    }
//
//    class UserView(val ctx: Context) : RelativeLayout(ctx), BaseItemView<User> {
//        override lateinit var viewBinding: ViewDataBinding
//
//        override fun setBinding(binding: ViewDataBinding) {
//             this@UserView.viewBinding = DataBindingUtil.inflate<TestItemBinding>(LayoutInflater.from(ctx), R.layout.test_item, this, false)
//        }
//
//        override fun bind(element: User, position: Int) {
//
//        }
//    }
//
//    class User(val name: ObservableField<String>) : BaseObservable()
//
//}
//
//interface TestContract {
//    interface Presenter : BasePresenter {
//        fun update(user: TestActivity.User)
//        fun dowload(path: String)
//    }
//
//    interface View : BaseView<Presenter> {
//        fun onDownLoadCompleted()
//        fun onUpdateCompleted( result : String)
//    }
//}
//
//class TestPresenter(val view : TestContract.View) : TestContract.Presenter {
//
//    override fun subscribe() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun unsubscribe() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun update(user: TestActivity.User) {
//
//        view.onUpdateCompleted("result")
//    }
//
//    override fun dowload(path: String)  {
//
//    }
//
//
//
//}
//
