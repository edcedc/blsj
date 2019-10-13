package com.hazz.kotlinmvp.api

import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.Utils
import com.yc.pyq.bean.BaseListBean
import com.yc.pyq.bean.BaseResponseBean
import com.yc.pyq.bean.DataBean
import com.yc.pyq.utils.cache.ShareSessionIdCache
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*


/**
 * Created by xuhao on 2017/11/16.
 * Api 接口
 */

interface ApiService{

    //注册
    @GET("user/register")
    fun userRegister(@Query("mobile") mobile : String, @Query("code") code : String, @Query("password") password : String, @Query("repeatPassword") repeatPassword : String): Observable<BaseResponseBean<Object>>
    //找回密码
    @GET("user/updateForgetPassword")
    fun userUpdateForgetPassword(@Query("mobile") mobile : String, @Query("code") code : String, @Query("password") password : String, @Query("repeatPassword") repeatPassword : String): Observable<BaseResponseBean<Object>>
     //修改密码
    @GET("user/updatePassword")
    fun userUpdatePassword(@Query("originalPassword") originalPassword : String, @Query("password") password : String, @Query("confirmPassword") confirmPassword : String, @Query("sessionId") sessionId: String? = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<Object>>
    //注册获取验证码
    @GET("user/getRegisterCode")
    fun userGetRegisterCode(@Query("mobile") mobile : String): Observable<BaseResponseBean<Object>>
    //更换绑定手机号获取号码 验证码接口
    @GET("user/getBindingPhoneCode")
    fun userGetBindingPhoneCode(@Query("mobile") mobile : String, @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<Object>>
    //意见箱 提交投诉/举报
    @GET("problem/save")
    fun problemSave(@Query("content") mobile : String,@Query("phone") phone : String, @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<Object>>
    //绑定手机号
    @GET("user/bindingPhone")
    fun userBindingPhone(@Query("mobile") mobile : String, @Query("code") code : String, @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<Object>>
    //找回密码获取验证码
    @GET("user/getForgetCode")
    fun userGetForgetCode(@Query("mobile") mobile : String): Observable<BaseResponseBean<Object>>
    //登录
    @GET("user/login")
    fun userLogin(@Query("mobile") mobile : String, @Query("password") password : String): Observable<ResponseBody>
    //注册协议
    @GET("information/agreement")
    fun informationAgreement(): Observable<BaseResponseBean<DataBean>>
    //圈子首页
    @GET("circle/page")
    fun circlePage(@Query("pageNumber") pageNumber : Int, @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<BaseListBean<DataBean>>>
     //常见问题列表
    @GET("information/pageIssue")
    fun informationPageIssue(@Query("pageNumber") pageNumber : Int): Observable<BaseResponseBean<BaseListBean<DataBean>>>
    //关注人的圈子
    @GET("circle/pageFocus")
    fun circlePageFocus(@Query("pageNumber") pageNumber : Int, @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<BaseListBean<DataBean>>>
    //发布动态
    @GET("circle/home")
    fun circleHome(@Query("pageNumber") pageNumber: Int, @Query("byUserId") byUserId: String? = null, @Query("sessionId") sessionId: String? = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<BaseListBean<DataBean>>>
    //我关注的人列表
    @GET("user/follow/page")
    fun userFollowPage(@Query("pageNumber") pageNumber: Int, @Query("sessionId") sessionId: String? = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<BaseListBean<DataBean>>>
    //用户详情
    @GET("user/information")
    fun userInformation(@Query("byUserId") byUserId: String? = null, @Query("sessionId") sessionId: String? = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<DataBean>>
    //是否关注
    @GET("user/follow/follow")
    fun followFollow(@Query("byUserId") byUserId: String? = null, @Query("sessionId") sessionId: String? = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<DataBean>>
    //圈子轮播图
    @GET("adv/adv/list")
    fun advAdvList(): Observable<BaseResponseBean<List<DataBean>>>
    //圈子评论列表
    @GET("circle/comment/page")
    fun circleCommentPage(@Query("circleId") circleId : String, @Query("pageNumber") pageNumber : Int, @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<BaseListBean<DataBean>>>
    //发布一级评论
    @GET("circle/comment/save")
    fun circleCommentSave(@Query("circleId") circleId : String? = null, @Query("content") content : String? = null, @Query("emojiContent") emojiContent : String = EncodeUtils.base64Encode2String(content?.toByteArray()), @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<DataBean>>
     //关于我们-简介
    @GET("information/introduction")
    fun informationIntroduction(): Observable<BaseResponseBean<DataBean>>
    //发布二级评论
    @GET("circle/comment/saveLevel")
    fun circleCommentSaveLevel(
        @Query("circleId") circleId : String? = null,
        @Query("replyUserId") replyUserId : String? = null,
        @Query("byReplyUserId") byReplyUserId : String? = null,
        @Query("parentId") parentId : String? = null,
        @Query("content") content : String? = null,
        @Query("emojiContent") emojiContent : String = EncodeUtils.base64Encode2String(content?.toByteArray()),
        @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId
    ): Observable<BaseResponseBean<DataBean>>
    //圈子点赞踩
    @GET("circle/savePraise")
    fun circleSavePraise(@Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId, @Query("circleId") circleId : String? = null, @Query("state") state : Int): Observable<BaseResponseBean<BaseListBean<DataBean>>>
    //圈子轮播图
    @GET("adv/adv/list")
    fun advList(): Observable<BaseResponseBean<List<DataBean>>>
    //识别是否URL
    @GET("circle/download")
    fun circleDownload(@Query("url")url : String? = null): Observable<BaseResponseBean<DataBean>>
    //发布圈子
    @Multipart
    @POST("circle/save")
    fun circleSave(
        @PartMap map : HashMap<String, RequestBody>? = null
        ): @JvmSuppressWildcards Observable<BaseResponseBean<DataBean>>
//
//    @Multipart
//    @POST("circle/save")
//    fun circleSave(
//        @Query("url")url : String? = null,
//        @Query("content")content : String? = null,
//        @Query("type")type : Int,
//        @Part("video")video : MultipartBody.Part? = null,
//        @Part("coverImage")coverImage : MultipartBody.Part? = null,
//        @PartMap map : HashMap<String, RequestBody>? = null,
//        @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId,
//        @Query("emojiContent") emojiContent : String = EncodeUtils.base64Encode2String(content?.toByteArray())
//        ): @JvmSuppressWildcards Observable<BaseResponseBean<String>>
    //更新头像
    @Multipart
    @POST("user/update")
    fun userUpdate(@Query("nick") nick : String,
                   @Part head : MultipartBody.Part,
                   @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<DataBean>>
    //更新个人信息
    @Multipart
    @POST("user/update")
    fun userUpdate(@PartMap map : HashMap<String, RequestBody>? = null,
                   @Query("sessionId") sessionId : String = ShareSessionIdCache.getInstance(Utils.getApp()).sessionId): Observable<BaseResponseBean<DataBean>>

//    @Multipart
//    @POST("information/saveInfoBack")
//    fun informationSaveInfoBack(@Query("context") context : String? = null,
//    @PartMap map : HashMap<String, RequestBody>): @JvmSuppressWildcards Observable<BaseResponseBean<Object>>



}