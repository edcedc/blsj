package com.yc.pyq.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.utils.cache.ShareSessionIdCache
import com.yc.pyq.weight.RuntimeRationale

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/22
 * Time: 18:15
 */
class SplashFrg : BaseFragment(){

    fun newInstance(): SplashFrg {
        val args = Bundle()
        val fragment = SplashFrg()
        fragment.arguments = args
        return fragment
    }

     val mHandle_splash = 0
     val mHandle_permission = 1

    override fun getLayoutId(): Int = R.layout.f_splash

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
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
        AndPermission.with(this@SplashFrg)
            .runtime()
            .permission(
                Manifest.permission.READ_EXTERNAL_STORAGE,//写入外部存储, 允许程序写入外部存储，如SD卡
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA//拍照权限, 允许访问摄像头进行拍照
            )
            .rationale(RuntimeRationale())
            .onGranted { setPermissionOk() }
            .onDenied { permissions ->
                if (AndPermission.hasAlwaysDeniedPermission(this@SplashFrg, permissions)) {
                    act?.let { showSettingDialog(it, permissions) }
                } else {
                    setPermissionCancel()
                }
            }
            .start()
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
            .start(1)
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
        val sessionId = ShareSessionIdCache.getInstance(act).getSessionId()
        if (!StringUtils.isEmpty(sessionId)) {

        } else {

        }
        startNext()
    }

    private fun startNext() {
        UIHelper.startMainAct()
        ActivityUtils.finishAllActivities()
    }

}