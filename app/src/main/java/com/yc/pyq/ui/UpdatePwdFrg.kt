package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.base.User
import com.yc.pyq.mvp.impl.UpdatePwdContract
import com.yc.pyq.mvp.presenter.OnePresenter
import com.yc.pyq.mvp.presenter.UpdatePwdPresenter
import kotlinx.android.synthetic.main.f_update_pwd.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 16:40
 *  修改密码
 */
class UpdatePwdFrg : BaseFragment(), UpdatePwdContract.View{

    override fun setSuccess() {
        pop()
    }

    val mPresenter by lazy { UpdatePwdPresenter() }


    override fun getLayoutId(): Int = R.layout.f_update_pwd

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.uptada_pwd))
        mPresenter.init(this, act)
        val userObj = User.getInstance().userObj
        tv_phone.text = "手机号码：" + userObj.optString("phone")
        bt_sure.setOnClickListener {
            mPresenter.onUpdatePassword(et_old_pwd.text.toString(), et_pwd.text.toString(), et_pwd1.text.toString())
        }
    }

}