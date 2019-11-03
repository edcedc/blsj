package com.yc.pyq.mvp.presenter

import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.yc.pyq.base.User
import com.yc.pyq.mvp.impl.AboutContract
import org.json.JSONObject

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 18:49
 */
class AboutPresenter : BasePresenter<AboutContract.View>(), AboutContract.Presenter{

    override fun onIntroduction() {
        val disposable = RetrofitManager.service.informationIntroduction()
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ json ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    val bean = JSONObject(json.string())
                    if (bean.optInt("code") == ErrorStatus.SUCCESS){
                        var  data = bean.optJSONObject("data");
                        val introduction = data.optJSONObject("introduction")
                        mRootView?.setUrl(introduction.optString("content"))
                    }
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