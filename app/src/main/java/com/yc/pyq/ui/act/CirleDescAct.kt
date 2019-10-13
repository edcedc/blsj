package com.yc.pyq.ui.act

import android.os.Bundle
import com.yc.pyq.R
import com.yc.pyq.base.BaseActivity
import com.yc.pyq.ui.CirleDescFrg
import com.yc.pyq.ui.LoginFrg

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/29
 * Time: 16:12
 *  quanzi 圈子详情
 */
class CirleDescAct : BaseActivity(){

     var bean :String? = null

    override fun getLayoutId(): Int = R.layout.activity_main


    override fun initView() {
        val frg = CirleDescFrg::class.java.newInstance()
        var bundle = Bundle()
        bundle.putString("bean", bean)
        frg.arguments = bundle
        if (findFragment(CirleDescFrg::class.java) == null) {
            loadRootFragment(R.id.fl_container, frg)
        }
    }

    override fun initParms(bundle: Bundle) {
        bean =  bundle.getString("bean");
    }

}
