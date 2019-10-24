package com.yc.pyq.mvp.impl

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter
import com.luck.picture.lib.entity.LocalMedia
import java.util.ArrayList

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/24
 * Time: 19:53
 */
interface UserUpdateContract {

    interface View : IBaseView {

        fun onSucees()

    }

    interface Presenter : IPresenter<View> {

        fun onUpdate(localMediaList: ArrayList<LocalMedia>, name: String)

        fun onUptate(
            background: ArrayList<LocalMedia>?,
            head: ArrayList<LocalMedia>?,
            nick: String?,
            signature: String?,
            address: String
        )

    }

}