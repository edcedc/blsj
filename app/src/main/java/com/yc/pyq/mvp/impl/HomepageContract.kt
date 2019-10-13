package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseListView
import com.hazz.kotlinmvp.base.IListPresenter
import com.yc.pyq.bean.DataBean

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/8
 * Time: 14:25
 */
interface HomepageContract {

    interface View : IBaseListView {

        fun setUser(data: DataBean?)

        fun setFollow(desc: String?)

    }

    interface Presenter: IListPresenter<View> {

        fun onRequest(page: Int, byUserId: String?)

        fun onUserInformation(byUserId: String?)

        fun onFollow(follow: Int, byUserId: String?)

    }

}