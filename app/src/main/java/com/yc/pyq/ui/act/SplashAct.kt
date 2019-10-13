package com.yc.pyq.ui.act

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.widget.Toast
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yc.pyq.R
import com.yc.pyq.base.BaseActivity
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.mvp.impl.LoginContract
import com.yc.pyq.mvp.presenter.LoginPresenter
import com.yc.pyq.mvp.presenter.OnePresenter
import com.yc.pyq.ui.SplashFrg
import com.yc.pyq.utils.cache.ShareSessionIdCache
import com.yc.pyq.utils.cache.SharedAccount
import com.yc.pyq.weight.RuntimeRationale
import kotlinx.android.synthetic.main.f_login.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/22
 * Time: 18:11
 */
class SplashAct : BaseActivity(), LoginContract.View{


     val mPresenter by lazy { LoginPresenter() }

    var REQUEST_CODE_SETTING : Int = 1

    val mHandle_splash = 0
    val mHandle_permission = 1

    override fun getLayoutId(): Int = R.layout.f_splash

    override fun initParms(bundle: Bundle) {
    }

    override fun initView() {
        mPresenter.init(this, act)
        handler?.sendEmptyMessageDelayed(mHandle_permission, 1000)
    }

    private var handler: Handler? = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                mHandle_splash -> startNext()
                mHandle_permission -> setHasPermission()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (handler != null) {
            handler?.removeCallbacksAndMessages(null)
            handler = null
        }
    }

    /**
     * 设置权限
     */
    private fun setHasPermission() {
        AndPermission.with(this)
            .runtime()
            .permission(
                Manifest.permission.READ_EXTERNAL_STORAGE,//写入外部存储, 允许程序写入外部存储，如SD卡
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA//拍照权限, 允许访问摄像头进行拍照
            )
            .rationale(RuntimeRationale())
            .onGranted { setPermissionOk() }
            .onDenied { permissions ->
                if (AndPermission.hasAlwaysDeniedPermission(this, permissions)) {
                    showSettingDialog(this, permissions)
                } else {
                    setPermissionCancel()
                }
            }
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SETTING -> {
                showToast("The user comes back from the settings page.")
            }
        }
    }

    /**
     * Display setting dialog.
     */
    fun showSettingDialog(context: Context, permissions: List<String>) {
        val permissionNames = Permission.transformText(context, permissions)
        val message =
            context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames))

        AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(R.string.app_name)
            .setMessage(message)
            .setPositiveButton(R.string.setting,
                { dialog, which -> setPermission() })
            .setNegativeButton(R.string.cancel,
                { dialog, which -> setPermissionCancel() })
            .show()
    }

    /**
     * Set permissions.
     */
    private fun setPermission() {
        AndPermission.with(this)
            .runtime()
            .setting()
            .start(REQUEST_CODE_SETTING)
    }

    /**
     * 权限有任何一个失败都会走的方法
     */
    private fun setPermissionCancel() {
        act?.finish()
    }

    /**
     * 权限都成功
     */
    private fun setPermissionOk() {
        val account = SharedAccount.getInstance(act)
        val mobile = account.getMobile()
        val pwd = account.getPwd()
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(pwd)) {
            mPresenter.onLogin(mobile, pwd)
        }else{
            startNext()
        }
    }

    override fun errorText(text: String, errorCode: Int) {
        super.errorText(text, errorCode)
        startNext()
    }

    private fun startNext() {
        UIHelper.startLoginAct()
        ActivityUtils.finishAllActivities()
    }

    override fun setNameHead(state_name_head: String) {
        UIHelper.startMainAct()
        ActivityUtils.finishAllActivities()
    }

    override fun setError() {
        startNext()
        SharedAccount.getInstance(act).remove()
    }

}