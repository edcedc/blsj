package com.yc.pyq.mvp.presenter

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.luck.picture.lib.entity.LocalMedia
import com.yc.pyq.R
import com.yc.pyq.base.User
import com.yc.pyq.mvp.impl.UserUpdateContract
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/24
 * Time: 19:53
 */
class UserUptatePresenter : BasePresenter<UserUpdateContract.View>(), UserUpdateContract.Presenter {

    override fun onUptate(background: ArrayList<LocalMedia>?, head: ArrayList<LocalMedia>?, nick: String?, signature: String?) {
        val map = HashMap<String, RequestBody>()
        if (background != null && background.size != 0){
            val create = RequestBody.create(MediaType.parse("image/png"), File(background[0].compressPath))
            map?.put("background" +
                    "\";filename=\"" + File(background!![0].path).name, create)
        }
        if (head != null && head.size != 0){
            val create = RequestBody.create(MediaType.parse("image/png"), File(head[0].compressPath))
            map?.put("head" +
                    "\";filename=\"" + File(head!![0].path).name, create)
        }
        if (!StringUtils.isEmpty(nick)){
            map?.put("nick", toRequestBody(nick as String))
        }
        if (!StringUtils.isEmpty(signature)){
            map?.put("signature", toRequestBody(signature as String))
        }
        mRootView?.showLoading()
        val disposable = RetrofitManager.service.userUpdate(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS) {
                        val data = bean.data
                        val userObj = User.getInstance().userObj
                        if (background != null){
                            userObj.put("background", data?.background)
                        }
                        if (head != null){
                            userObj.put("head", data?.head)
                        }
                        if (nick != null){
                            userObj.put("nick", data?.nick)
                        }
                        if (signature != null){
                            userObj.put("signature", data?.signature)
                        }
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

    override fun onUpdate(localMediaList: ArrayList<LocalMedia>, name: String) {
        if (StringUtils.isEmpty(name) || localMediaList.size == 0){
            act?.getString(R.string.error_)?.let { showToast(it) }
            return
        }

        val create = RequestBody.create(MediaType.parse("image/png"), File(localMediaList[0].compressPath))
        val imgBody = MultipartBody.Part.createFormData("head", File(localMediaList[0].compressPath).name, create)

//        val map = HashMap<String, RequestBody>()
//        for (i in localMediaList.indices) {
//            val create = RequestBody.create(MediaType.parse("multipart/form-data"), File(localMediaList[i].path))
//            map.put("head", create)
//        }

        mRootView?.showLoading()
        val disposable = RetrofitManager.service.userUpdate(name, imgBody)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        mRootView?.onSucees()
                    }
                    showToast(bean.desc as String)
                }
            }, { t ->
                mRootView?.apply {
                    //处理异常
                    LogUtils.e(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }

            })

        addSubscription(disposable)
    }

    private fun toRequestBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }

}
