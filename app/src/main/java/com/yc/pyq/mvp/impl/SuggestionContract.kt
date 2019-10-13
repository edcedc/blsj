package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 18:11
 */
interface SuggestionContract {

    interface View : IBaseView {

        fun setSuccess()

    }

    interface Presenter: IPresenter<View> {

        fun onSure(content : String, phone : String)

    }

}