package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseListView
import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IListPresenter
import com.hazz.kotlinmvp.base.IPresenter
import com.yc.pyq.bean.DataBean

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 16:49
 */
interface UpdatePwdContract {

    interface View : IBaseView {
        fun setSuccess()

    }

    interface Presenter: IPresenter<View> {

        fun onUpdatePassword(originalPassword: String, password: String, confirmPassword: String)

    }

}