package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.StringUtils
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.mvp.impl.LoginContract
import com.yc.pyq.mvp.presenter.LoginPresenter
import com.yc.pyq.utils.cache.SharedAccount
import kotlinx.android.synthetic.main.f_login.*
import kotlinx.android.synthetic.main.f_login.bt_sure

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/22
 * Time: 10:01
 *  登录
 */
class LoginFrg : BaseFragment(),LoginContract.View, View.OnClickListener{
    override fun setError() {
    }

    private val mPresenter by lazy { LoginPresenter() }

    fun newInstance(): LoginFrg {
        val args = Bundle()
        val fragment = LoginFrg()
        fragment.arguments = args
        return fragment
    }

    override fun getLayoutId(): Int = R.layout.f_login

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitleCenter(getString(R.string.login))
        mPresenter.init(this, act)
        bt_sure.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        tv_forget.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.bt_sure -> mPresenter.onLogin(et_phone.text.toString(), et_pwd.text.toString())
            R.id.tv_register -> UIHelper.startRegisterFrg(this)
            R.id.tv_forget -> UIHelper.startForgetFrg(this)
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        val account = SharedAccount.getInstance(act)
        val mobile = account.getMobile()
        val pwd = account.getPwd()
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(pwd)) {
            et_phone.setText(mobile)
            et_pwd.setText(pwd)
        }
    }

    override fun setNameHead(nick: String) {
        if (nick.equals("null")){
            UIHelper.startNameHeadFrg(this)
        }else{
            UIHelper.startMainAct()
            act?.finish()
        }
    }

}