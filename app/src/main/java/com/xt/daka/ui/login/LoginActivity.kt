package com.xt.daka.ui.login

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import android.content.Intent
import android.graphics.Rect
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityOptionsCompat
import android.transition.Explode
import android.util.Log
import com.xt.daka.R
import com.xt.daka.base.BaseActivity

import kotlinx.android.synthetic.main.activity_login.*
import android.transition.Slide
import com.xt.daka.ui.sign.SignActivity
import com.xt.daka.ui.sign.SignException
import com.xt.daka.util.helper.toast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity(), LoginContract.View {

    val mPresenter: LoginContract.Presenter by lazy {
        LoginPresenter(this)
    }

    var dialog : ProgressDialog? = null

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListener()
        initAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unsubscribe()
    }

    override fun onStartLogin() {
        dialog = indeterminateProgressDialog("登录中")
    }

    override fun onLoginSuccess() {
        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this@LoginActivity).toBundle()
        startActivity(Intent(this@LoginActivity, SignActivity::class.java), bundle)
    }

    override fun onLoginFailed(error: Throwable) {
        if(error is LoginException) {
            when (error.status) {
                LoginError.ACCOUNT_INCORRECT -> toast("账户不存在")
                LoginError.PASSWORD_INCORRECT -> toast("密码错误,请重新输入")
                LoginError.CONNECT_TIMEOUT -> toast("连接超时")
            }
        }
        dialog?.dismiss()
    }



    fun initListener() {

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        login.setOnClickListener {
            attemptLogin()
        }

        login.requestFocus()

    }


    fun initAnimation() {
        window.enterTransition = Explode().setDuration(300)
        window.exitTransition = Explode().setDuration(300)
    }

    private fun attemptLogin() {

        // Reset errors.
        account.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val accountStr = account.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(accountStr)) {
            account.error = getString(R.string.error_field_required)
            focusView = account
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
            return
        }

        mPresenter.login(accountStr, passwordStr)

    }

    private fun isPasswordValid(password: String): Boolean {
        return true
    }

    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        val adapter = ArrayAdapter(this@LoginActivity,
                android.R.layout.simple_dropdown_item_1line, emailAddressCollection)
        account.setAdapter(adapter)
    }

    var initHeight = 0
    var DIF = 0
    private fun test() {
        val decorView = window.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener {

            Log.e("LoginActivity", "TREE")
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            if (initHeight == 0) {
                initHeight = rect.height()
            }

            val inputBoxHeight = initHeight - rect.height()

            if (inputBoxHeight > 0) {
                content.layout(content.left, content.top - inputBoxHeight + DIF, content.right, content.bottom - inputBoxHeight + DIF)

//                    ValueAnimator.ofInt(0,content.top - inputBoxHeight + DIF).setDuration(1000).addUpdateListener {
//                        valueAnimator ->  content.translationY = valueAnimator.getAnimatedValue()
//                    }
//                    Log.e("eee",(content.top - inputBoxHeight + DIF).toString())
//                    val anim = ValueAnimator.ofInt(tempH,content.top - inputBoxHeight + DIF).setDuration(200)
//                    anim.addUpdateListener {
//                        valueAnimator -> content.translationY = java.lang.Float.parseFloat((valueAnimator.animatedValue).toString())
//                        if(valueAnimator.animatedFraction == 1.0f) tempH = content.translationY.toInt()
//                    }
//                    anim.start()
//                    Log.e("LoginActivity","open")

            } else {

                content.layout(content.left, content.top + DIF, content.right, content.bottom + DIF)
//                    val anim = ValueAnimator.ofInt(content.top,tempH).setDuration(200)
//                    anim.addUpdateListener{
//                        valueAnimator ->  content.translationY = java.lang.Float.parseFloat((valueAnimator.animatedValue).toString())
//                        if(valueAnimator.animatedFraction == 1.0f) tempH = content.translationY.toInt()
//                    }
//                    anim.start()
                Log.e("LoginActivity", "close")
            }

        }

        smallCard.viewTreeObserver.addOnGlobalLayoutListener {
            DIF = (smallCard.layoutParams as ConstraintLayout.LayoutParams).bottomMargin
        }

    }

}