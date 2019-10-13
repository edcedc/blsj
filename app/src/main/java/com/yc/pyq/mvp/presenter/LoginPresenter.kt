package com.yc.pyq.mvp.presenter

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.yc.pyq.base.User
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.mvp.impl.LoginContract
import com.yc.pyq.utils.cache.ShareSessionIdCache
import com.yc.pyq.utils.cache.SharedAccount
import org.json.JSONObject

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/19
 * Time: 17:05
 */
class LoginPresenter : BasePresenter<LoginContract.View>(),LoginContract.Presenter{

        override fun onLogin(phone : String, pwd : String) {

            val disposable = RetrofitManager.service.userLogin(phone, pwd)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ json ->
                    mRootView?.apply {
//                        LogUtils.e()
                        val bean = JSONObject(json.string())
                        if (bean.optInt("code") == ErrorStatus.SUCCESS){
                            val data = bean.optJSONObject("data")
                            if (data.optInt("code") == ErrorStatus.SUCCESS){
                                val  user = data.optJSONObject("data")
                                val userExtend = user.optJSONObject("userExtend")
                                userExtend.put("phone", user.optString("username"))
                                User.getInstance().userObj = userExtend
                                User.getInstance().isLogin = true
                                ShareSessionIdCache.getInstance(Utils.getApp()).save(user.optString("sessionId"))
                                SharedAccount.getInstance(Utils.getApp()).save(phone, pwd)
                                mRootView?.setNameHead(data.optString("nick"))
                            }
                        }else{
                            mRootView?.setError()
                            showToast(bean.optString("desc"))
                        }
                    }
                }, { t ->
                    mRootView?.apply {
                        //处理异常
                        mRootView?.errorText(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }

                })

            addSubscription(disposable)

  /*   val create = RequestBody.create(MediaType.parse("multipart/form-data"), File(localMediaList[0].path))
       val imgBody = MultipartBody.Part.createFormData("image", "不知传什么",  create)

        val map = HashMap<String, RequestBody>()
        for (i in localMediaList.indices) {
            val create = RequestBody.create(MediaType.parse("multipart/form-data"), File(localMediaList[i].path))
            map.put("file" + i, create)
        }*/
    }

}