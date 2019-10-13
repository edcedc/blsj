package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.tencent.bugly.proguard.ac
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.mvp.impl.SuggestionContract
import com.yc.pyq.mvp.presenter.BingPhonePresenter
import com.yc.pyq.mvp.presenter.SuggestionPresenter
import kotlinx.android.synthetic.main.f_suggestion.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 18:00
 *  意见箱
 */
class SuggestionFrg : BaseFragment(), SuggestionContract.View{

    val mPresenter by lazy { SuggestionPresenter() }

    override fun getLayoutId(): Int = R.layout.f_suggestion

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.suggestion))
        mPresenter.init(this, act)
        bt_sure.setOnClickListener { mPresenter.onSure(et_text.text.toString(), et_content.text.toString()) }
    }

    override fun setSuccess() {
        pop()
    }


}