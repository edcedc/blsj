package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.base.User
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.utils.PictureSelectorTool
import com.yc.pyq.utils.PopupWindowTool
import com.yc.pyq.utils.cache.SharedAccount
import kotlinx.android.synthetic.main.f_set.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 16:14
 */
class SetFrg : BaseFragment(), View.OnClickListener{


    fun newInstance(): SetFrg {
        val args = Bundle()
        val fragment = SetFrg()
        fragment.arguments = args
        return fragment
    }

    override fun getLayoutId(): Int = R.layout.f_set

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.set))
        tv_pwd.setOnClickListener(this)
        tv_phone.setOnClickListener(this)
        tv_problems.setOnClickListener(this)
        tv_suggestion.setOnClickListener(this)
        tv_about.setOnClickListener(this)
        tv_out.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_pwd ->{
                UIHelper.startUpdatePwdFrg(this)
            }
            R.id.tv_phone ->{
                UIHelper.startBingPhoneFrg(this)
            }
            R.id.tv_problems ->{
                UIHelper.startProblemsFrg(this)
            }
            R.id.tv_suggestion ->{
                UIHelper.startSuggestionFrg(this)
            }
            R.id.tv_about ->{
                UIHelper.startAboutFrg(this)
            }
            R.id.tv_out ->{
                PopupWindowTool.showDialog(act).asConfirm("是否退出?", "", "取消", "确定", {
                    SharedAccount.getInstance(act).remove()
                    User.getInstance().isLogin = false
                    User.getInstance().userObj = null
                    UIHelper.startLoginAct()
                    ActivityUtils.finishAllActivities()
                }, null, false).bindLayout(R.layout.p_dialog).show()

            }
        }
    }

}