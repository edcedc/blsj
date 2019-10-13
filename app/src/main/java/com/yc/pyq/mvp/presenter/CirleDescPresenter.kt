package com.yc.pyq.mvp.presenter

import com.blankj.utilcode.util.LogUtils
import com.hazz.kotlinmvp.base.BaseListPresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.yc.pyq.base.User
import com.yc.pyq.mvp.impl.CirleDescContract

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/29
 * Time: 16:22
 */
class CirleDescPresenter: BaseListPresenter<CirleDescContract.View>(), CirleDescContract.Presenter{

    override fun onSecondComment(
        position: Int,
        id: String?,
        userId: String?,
        byReplyUserId: String?,
        parentId: String?,
        text: String?
    ) {
        val disposable = RetrofitManager.service.circleCommentSaveLevel(id, userId, byReplyUserId, parentId, text)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        val user = User.getInstance().userObj
                        val data = bean.data
                        data?.nick = user.optString("nick")
                        data?.head = user.optString("head")
                        mRootView?.setFirstSend(position, data)
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

    override fun onFirstSend(position: Int, id: String?, text: String?) {
        val disposable = RetrofitManager.service.circleCommentSave(id, text)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        val user = User.getInstance().userObj
                        val data = bean.data
                        data?.nick = user.optString("nick")
                        data?.head = user.optString("head")
                        mRootView?.setFirstSend(position, data)
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

    override fun onRequest(page: Int, id: String?) {
        val disposable = RetrofitManager.service.circleCommentPage(id as String, page)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        val data = bean.data
                        val list = data?.list
                        if (list != null && list.size != 0){
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

    override fun onSsavePraise(position: Int, circleId: String?, praise: Int) {
        val disposable = RetrofitManager.service.circleSavePraise(circleId = circleId, state = praise)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    if (bean.code == ErrorStatus.SUCCESS){
                        mRootView?.setSavePraise(position, praise)
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