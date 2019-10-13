package com.yc.pyq.controller

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.google.gson.Gson
import com.yc.pyq.MainActivity
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.ui.*
import com.yc.pyq.ui.act.*


/**
 * Created by Administrator on 2017/2/22.
 */

class UIHelper private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        fun startMainAct() {
            ActivityUtils.startActivity(MainActivity::class.java)
        }

       fun startLoginAct() {
            ActivityUtils.startActivity(LoginAct::class.java)
        }

        /**
         *  发布
         */
       fun startReleaseAct() {
            ActivityUtils.startActivity(ReleaseAct::class.java)
        }

        /**
         *  圈子详情
         */
       fun startCirleDescAct(bean: DataBean) {
            var bundle = Bundle()
            bundle.putString("bean", Gson().toJson(bean))
            ActivityUtils.startActivity(bundle, CirleDescAct::class.java)
        }

        /**
         *  各种H5
         */
       fun startHtmlAct(type : Int) {
           val bundle = Bundle()
           bundle.putInt("type", type)
           ActivityUtils.startActivity(bundle, HtmlAct::class.java)
        }
       fun startHtmlAct(type : Int, url : String?) {
           val bundle = Bundle()
           bundle.putInt("type", type)
           bundle.putString("url", url)
           ActivityUtils.startActivity(bundle, HtmlAct::class.java)
        }
       fun startHtmlAct(type : Int, url : String?, title: String?) {
           val bundle = Bundle()
           bundle.putInt("type", type)
           bundle.putString("url", url)
           bundle.putString("title", title)
           ActivityUtils.startActivity(bundle, HtmlAct::class.java)
        }

        /**
         *  注册
         */
        fun startRegisterFrg(root : BaseFragment){
            val frg = RegisterFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            root.start(frg)
        }

        /**
         *  忘记密码
         */
        fun startForgetFrg(root : BaseFragment){
            val frg = ForgetFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            root.start(frg)
        }

        /**
         *  设置头像和昵称
         */
        fun startNameHeadFrg(root : BaseFragment) {
            val frg = NameHeadFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            root.start(frg)
//            ActivityUtils.startActivity(NameHeadFrg::class.java)
        }

        /**
         *  个人主页
         */
        fun startHomepageFrg(root: BaseFragment, byUserId: String?) {
            val frg = HomepageFrg()
            val bundle = Bundle()
            bundle.putString("byUserId", byUserId)
            frg.setArguments(bundle)
            val fragment = root.parentFragment
            if (fragment == null) {
                root.start(frg)
            } else {
                (root.parentFragment as MainFrg).startBrotherFragment(frg)
            }
        }

        /**
         *  我关注的人
         */
        fun startFollowFrg(root: BaseFragment) {
            val frg = FollowFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            val fragment = root.parentFragment
            if (fragment == null) {
                root.start(frg)
            } else {
                (root.parentFragment as MainFrg).startBrotherFragment(frg)
            }
        }

        /**
         *  修改个人信息
         */
        fun startUpdateUserFrg(root: BaseFragment) {
            val frg = UpdateUserFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            val fragment = root.parentFragment
            if (fragment == null) {
                root.start(frg)
            } else {
                (root.parentFragment as MainFrg).startBrotherFragment(frg)
            }
        }

        /**
         *  设置
         */
        fun startSetAct() {
            ActivityUtils.startActivity(SetAct::class.java)
        }

        /**
         *  修改密码
         */
        fun startUpdatePwdFrg(root: BaseFragment) {
            val frg = UpdatePwdFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            root.start(frg)
        }

        /**
         *  绑定手机号
         */
        fun startBingPhoneFrg(root: BaseFragment) {
            val frg = BingPhoneFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            root.start(frg)
        }

        /**
         *  常见问题
         */
        fun startProblemsFrg(root: BaseFragment) {
            val frg = ProblemsFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            root.start(frg)
        }

        /**
         *  意见箱
         */
        fun startSuggestionFrg(root: BaseFragment) {
            val frg = SuggestionFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            root.start(frg)
        }

        /**
         *  关于我们
         */
        fun startAboutFrg(root: BaseFragment) {
            val frg = AboutFrg()
            val bundle = Bundle()
            frg.setArguments(bundle)
            root.start(frg)
        }

    }

}