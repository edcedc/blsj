package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseListView
import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IListPresenter
import com.hazz.kotlinmvp.base.IPresenter
import com.yc.pyq.bean.DataBean

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/25
 * Time: 11:46
 */
interface OneContract {

    interface View : IBaseListView {

        fun setBanner(list: List<DataBean>)
        fun setSavePraise(position: Int, praise: Int)

    }

    interface Presenter: IListPresenter<View> {

        fun onRequest(page: Int)
        fun onPageFocus(page: Int)

        fun onBanner()

        fun onSsavePraise(position: Int, circleId: String, praise: Int)

    }

}