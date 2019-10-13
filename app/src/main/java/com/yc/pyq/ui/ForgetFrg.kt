package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.mvp.impl.ForgetContract
import com.yc.pyq.mvp.presenter.ForgetPresenter
import com.yc.pyq.utils.CountDownTimerUtils
import kotlinx.android.synthetic.main.f_register.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/23
 * Time: 19:12
 *  忘记密码
 */
class ForgetFrg : BaseFragment(), ForgetContract.View, View.OnClickListener{

    override fun setCode() {
        CountDownTimerUtils(act, 60000, 1000, tv_code).start()
    }

    private val mPresenter by lazy { ForgetPresenter() }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_code -> {mPresenter.onCode(et_phone?.text.toString())}
            R.id.bt_sure -> {mPresenter.onSure(et_phone?.text.toString(), et_code?.text.toString(), et_pwd?.text.toString(), et_pwd1?.text.toString())}
        }
    }

    override fun getLayoutId(): Int = R.layout.f_register

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.forget))
        mPresenter.init(this, act)
        tv_code.setOnClickListener(this)
        bt_sure.setOnClickListener(this)
        cb_agreement.visibility = View.GONE
    }

}