package com.yc.pyq.mvp.presenter

import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.yc.pyq.R
import com.yc.pyq.base.User
import com.yc.pyq.mvp.impl.UpdatePwdContract
import com.yc.pyq.utils.cache.ShareSessionIdCache
import com.yc.pyq.utils.cache.SharedAccount
import org.json.JSONObject

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 16:51
 */
class UpdatePwdPresenter :BasePresenter<UpdatePwdContract.View>(), UpdatePwdContract.Presenter{

    override fun onUpdatePassword(originalPassword: String, password: String, confirmPassword: String) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword)){
            showToast(act!!.getString(R.string.error_))
            return
        }
        if (!password.equals(confirmPassword)){
            showToast("两次密码不一致")
            return
        }
        mRootView?.showLoading()
        val disposable = RetrofitManager.service.userUpdatePassword(originalPassword, password, confirmPassword)
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