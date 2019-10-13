package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/23
 * Time: 19:15
 */
interface ForgetContract{

    interface View : IBaseView {
        fun setCode()
    }

    interface Presenter: IPresenter<View> {

        fun onCode(phone : String)

        fun onSure(
            phone: String,
            code: String,
            pwd: String,
            pwd1: String
        )

    }

}
