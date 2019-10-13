package com.yc.pyq.mvp.presenter

import com.blankj.utilcode.util.StringUtils
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.yc.pyq.R
import com.yc.pyq.mvp.impl.SuggestionContract

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 18:13
 */
class SuggestionPresenter : BasePresenter<SuggestionContract.View>(), SuggestionContract.Presenter{

    override fun onSure(content: String, phone: String) {
        if (StringUtils.isEmpty(content)) {
            showToast(act?.resources?.getString(R.string.error_) as  String)
            return
        }
        mRootView?.showLoading()
        var disposable = RetrofitManager.service.problemSave(content, phone)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        mRootView?.setSuccess()
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