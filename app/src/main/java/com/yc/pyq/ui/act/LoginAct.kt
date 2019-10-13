package com.yc.pyq.ui.act

import android.content.Intent
import android.os.Bundle
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.yc.mema.event.CameraInEvent
import com.yc.pyq.R
import com.yc.pyq.base.BaseActivity
import com.yc.pyq.mvp.impl.LoginContract
import com.yc.pyq.mvp.presenter.LoginPresenter
import com.yc.pyq.ui.LoginFrg
import com.yc.pyq.utils.PictureSelectorTool
import kotlinx.android.synthetic.main.f_login.*
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/18
 * Time: 21:00
 *  登录
 */
class LoginAct : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initParms(bundle: Bundle) {
    }

    override fun initView() {
        if (findFragment(LoginFrg::class.java) == null) {
            loadRootFragment(R.id.fl_container, LoginFrg::class.java.newInstance())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            EventBus.getDefault().post(data?.let { CameraInEvent(requestCode, it) })
        }
    }

}
