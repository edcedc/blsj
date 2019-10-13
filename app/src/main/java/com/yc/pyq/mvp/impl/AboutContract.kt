package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 18:49
 */
interface AboutContract {

    interface View : IBaseView {

        fun setUrl(content: String?)

    }

    interface Presenter: IPresenter<View> {

        fun onIntroduction()

    }

}