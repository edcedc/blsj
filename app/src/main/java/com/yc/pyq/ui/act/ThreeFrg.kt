package com.yc.pyq.ui.act

import android.os.Bundle
import android.view.View
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.base.User
import com.yc.pyq.controller.CloudApi
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.weight.GlideLoadingUtils
import kotlinx.android.synthetic.main.f_three.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/25
 * Time: 11:09
 */
class ThreeFrg : BaseFragment(), View.OnClickListener{

    fun newInstance(): ThreeFrg {
        val args = Bundle()
        val fragment = ThreeFrg()
        fragment.arguments = args
        return fragment
    }

    override fun getLayoutId(): Int = R.layout.f_three

    override fun initParms(bundle: Bundle) {

    }

    override fun initView(rootView: View) {
        setSwipeBackEnable(false)
        tv_dynamic.setOnClickListener(this)
        tv_follow.setOnClickListener(this)
        tv_set.setOnClickListener(this)
        fy_close.setOnClickListener(this)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        setSofia(true)
        val userObj = User.getInstance().userObj
        GlideLoadingUtils.loadRounded(act, CloudApi.SERVLET_IMG_URL + userObj.optString("head"), iv_head)
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + userObj.optString("background"), iv_img)
        tv_name.text = userObj.optString("nick")
        tv_nick.text = userObj.optString("signature")
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_dynamic ->{
                UIHelper.startHomepageFrg(this, null)
            }
            R.id.tv_follow ->{
                UIHelper.startFollowFrg(this)
            }
            R.id.tv_set ->{
                UIHelper.startSetAct()
            }
             R.id.fy_close ->{
                UIHelper.startUpdateUserFrg(this)
            }
        }
    }


}
