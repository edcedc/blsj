package com.hazz.kotlinmvp.base

import com.yc.pyq.bean.DataBean
import java.util.*

/**
 * @author Jake.Ho
 * created: 2017/10/25
 * desc:
 */
interface IBaseListView {

    fun showLoading()

    fun hideLoading()

    fun setRefreshLayoutMode(totalRow : Int)

    fun errorText(text : String, errorCode : Int)

    fun setData(objects: Object)

}
