package com.yc.pyq.ui.act

import android.content.Intent
import android.os.Bundle
import com.yc.mema.event.CameraInEvent
import com.yc.pyq.R
import com.yc.pyq.base.BaseActivity
import com.yc.pyq.ui.LoginFrg
import com.yc.pyq.ui.SetFrg
import org.greenrobot.eventbus.EventBus

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 16:13
 */
class SetAct: BaseActivity(){

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initParms(bundle: Bundle) {
    }

    override fun initView() {
        if (findFragment(SetFrg::class.java) == null) {
            loadRootFragment(R.id.fl_container, SetFrg::class.java.newInstance())
        }
    }

}
