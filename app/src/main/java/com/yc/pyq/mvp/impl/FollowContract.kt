package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseListView
import com.hazz.kotlinmvp.base.IListPresenter
import com.yc.pyq.bean.DataBean

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/12
 * Time: 23:48
 */
interface FollowContract {

    interface View : IBaseListView {
        fun setFollow(position: Int)

    }

    interface Presenter: IListPresenter<View> {

        fun onRequest(page: Int)

        fun onFollow(position: Int, byUserId: String?)

    }

}