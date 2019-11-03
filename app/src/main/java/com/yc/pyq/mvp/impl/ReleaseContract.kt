package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter
import com.luck.picture.lib.entity.LocalMedia
import com.yc.pyq.bean.DataBean
import java.util.ArrayList

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/30
 * Time: 11:47
 */
interface ReleaseContract {

    interface View : IBaseView {
        fun setDow(title: String?)
    }

    interface Presenter: IPresenter<View> {

        fun onRelease(
            type: Int,
            content: String? = null,
            list: ArrayList<LocalMedia>? = null,
            url: String? = null
        )

        fun onCircleDownload(url: String?)

    }
}