package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseListView
import com.hazz.kotlinmvp.base.IListPresenter
import com.yc.pyq.bean.DataBean

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 17:38
 */
interface ProblemsContract {
    interface View : IBaseListView {


    }

    interface Presenter: IListPresenter<View> {

        fun onRequest(page: Int)

    }
}