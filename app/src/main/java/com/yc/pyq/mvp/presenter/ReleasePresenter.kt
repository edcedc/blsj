package com.yc.pyq.mvp.presenter

import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.luck.picture.lib.entity.LocalMedia
import com.yc.pyq.R
import com.yc.pyq.mvp.impl.ReleaseContract
import com.yc.pyq.utils.cache.ShareSessionIdCache
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/30
 * Time: 11:47
 */
class ReleasePresenter : BasePresenter<ReleaseContract.View>(), ReleaseContract.Presenter{

    override fun onCircleDownload(url: String?) {
        mRootView?.showLoading()
        val disposable = RetrofitManager.service.circleDownload(url)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        val data = bean.data
                        if (data?.title != null){
                            mRootView?.setDow(data?.title)
                        }else{
                            showToast("识别出错，请重新选择URL")
                        }
                    }
//                    showToast(bean.desc as String)
                }
            }, { t ->
                mRootView?.apply {
                    //处理异常
                    mRootView?.errorText(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }

            })
        addSubscription(disposable)
    }

    override fun onRelease(
        type: Int,
        content: String?,
        localMediaList: ArrayList<LocalMedia>?,
        url: String?
    ) {
        if (StringUtils.isEmpty(content) && localMediaList?.size == 0 && StringUtils.isEmpty(url)){
            showToast(act?.getString(R.string.yyc_7) as String)
            return
        }
        val map = HashMap<String, RequestBody>()
        map.put("type", toRequestBody(type.toString()))
        map.put("content", toRequestBody(content as String))
        map.put("emojiContent", toRequestBody(EncodeUtils.base64Encode2String(content?.toByteArray())))
        map.put("sessionId", toRequestBody(ShareSessionIdCache.getInstance(Utils.getApp()).sessionId))
        when(type){//1图文链接，2相片，3视频，4纯文字
            1 ->{
                map.put("url", toRequestBody(url as String))
            }
            2 -> {
                for (i in localMediaList!!.indices) {
                    LogUtils.e(File(localMediaList[i].path))
                    val create = RequestBody.create(MediaType.parse("image/png"), File(localMediaList[i].compressPath))
//                    map?.put("image" + (i + 1), create)
                    map?.put("image" + (i + 1) +
                            "\";filename=\"" + File(localMediaList!![0].path).name, create)
                }
            }
            3 -> {
                val create_video = RequestBody.create(MediaType.parse("image/png"), File(localMediaList!![0].path))
//                body_video = MultipartBody.Part.createFormData("video", File(localMediaList!![0].path).name, create_video)

                val create_img = RequestBody.create(MediaType.parse("image/png"), File(localMediaList!![0].cutPath))
//                body_img = MultipartBody.Part.createFormData("coverImage", File(localMediaList!![0].cutPath).name, create_img)

                map?.put("video" +
                        "\";filename=\"" + File(localMediaList!![0].path).name, create_video)
                map?.put("coverImage" +
                        "\";filename=\"" + File(localMediaList!![0].path).name, create_img)
            }
        }

        mRootView?.showLoading()
        val disposable = RetrofitManager.service.circleSave(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ bean ->
                mRootView?.apply {
                    mRootView?.hideLoading()
                    if (bean.code == ErrorStatus.SUCCESS){
                        act?.finish()
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

    private fun toRequestBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }

}