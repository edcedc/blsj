package com.yc.pyq.mvp.presenter

import com.blankj.utilcode.util.StringUtils
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.yc.pyq.R
import com.yc.pyq.mvp.impl.BingPhoneContract

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 17:17
 */
class BingPhonePresenter : BasePresenter<BingPhoneContract.View>(), BingPhoneContract.Presenter{

    override fun onCode(phone: String) {
        if (StringUtils.isEmpty(phone)) {
            showToast(act?.resources?.getString(R.string.please_phone) as  String)
            return
        }
        mRootView?.showLoading()
        var disposable = RetrofitManager.service.userGetBindingPhoneCode(phone)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        mRootView?.setCode()
                    }
                    showToast(bean.desc as String)
                }
            }, { t ->
                mRootView?.apply {
                    //处理异常
                    mRootView?.errorText(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })

        addSubscription(disposable)
    }

    override fun onSure(phone: String, code: String) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            showToast(act?.resources?.getString(R.string.error_) as  String)
            return
        }
        mRootView?.showLoading()
        var disposable = RetrofitManager.service.userBindingPhone(phone, code)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        mRootView?.setSuccer()
                    }
                    showToast(bean.desc as String)
                }
            }, { t ->
                mRootView?.apply {
                    //处理异常
                    mRootView?.errorText(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })

        addSubscription(disposable)
    }

}