package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.mvp.impl.BingPhoneContract
import com.yc.pyq.mvp.presenter.BingPhonePresenter
import com.yc.pyq.mvp.presenter.RegisterPresenter
import com.yc.pyq.utils.CountDownTimerUtils
import kotlinx.android.synthetic.main.f_bing_phone.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 17:08
 */
class BingPhoneFrg : BaseFragment(), BingPhoneContract.View{

    override fun setSuccer() {
        pop()
    }

     val mPresenter by lazy { BingPhonePresenter() }


    override fun getLayoutId(): Int = R.layout.f_bing_phone

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.bind_phone))
        mPresenter.init(this, act)
        tv_code.setOnClickListener { mPresenter.onCode(et_phone.text.toString()) }
        bt_sure.setOnClickListener { mPresenter.onSure(et_phone.text.toString(), et_code.text.toString()) }
    }

    override fun setCode() {
        CountDownTimerUtils(act, 60000, 1000, tv_code).start()
    }

}