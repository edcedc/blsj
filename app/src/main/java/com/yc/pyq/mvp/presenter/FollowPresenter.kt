package com.yc.pyq.mvp.presenter

import com.hazz.kotlinmvp.base.BaseListPresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.yc.pyq.mvp.impl.FollowContract
import com.yc.pyq.mvp.impl.ForgetContract

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/12
 * Time: 23:50
 */
class FollowPresenter : BaseListPresenter<FollowContract.View>(), FollowContract.Presenter {

    override fun onRequest(page: Int) {
        val disposable = RetrofitManager.service.userFollowPage(page)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    if (bean.code == ErrorStatus.SUCCESS){
                        val data = bean.data
                        val list = data?.list
                        if (list != null){
                            mRootView?.setData(list as Object)
                        }
                        mRootView?.setRefreshLayoutMode(data?.totalRow as Int)
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

    override fun onFollow(position: Int, byUserId: String?) {
        mRootView?.showLoading()
        val disposable = RetrofitManager.service.followFollow(byUserId)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        mRootView?.setFollow(position)
                    }
                    bean.desc?.let { showToast(it) }
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