package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/19
 * Time: 17:02
 */
interface LoginContract{

    interface View : IBaseView{

        fun setNameHead(state_name_head: String)
        fun setError()

    }

    interface Presenter: IPresenter<View> {

        fun onLogin(phone : String, pwd : String)

    }

}