package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseListView
import com.hazz.kotlinmvp.base.IListPresenter
import com.yc.pyq.bean.DataBean

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/29
 * Time: 16:22
 */
interface CirleDescContract {

    interface View : IBaseListView {

        fun setSavePraise(position: Int, praise: Int)
        fun setFirstSend(position: Int, data: DataBean?)
    }

    interface Presenter : IListPresenter<View> {

        fun onRequest(page: Int, id: String?)

        fun onSsavePraise(position: Int, circleId: String? = null, praise: Int)
        fun onFirstSend(position: Int, id: String? = null, text: String? = null)
        fun onSecondComment(
            position: Int,
            id: String? = null,
            userId: String? = null,
            byReplyUserId: String? = null,
            parentId: String? = null,
            text: String? = null
        )

    }

}