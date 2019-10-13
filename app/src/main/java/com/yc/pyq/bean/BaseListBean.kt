package com.yc.pyq.bean

import java.io.Serializable

/**
 * Created by yc on 2017/8/17.
 */

class BaseListBean<T> : Serializable {

    var totalRow: Int = 0
    var totalPage: Int = 0
    var pageNumber: Int = 0
    var pageSize: Int = 0
    var list: List<T>? = null

    override fun toString(): String {
        return "BaseListBean(totalRow=$totalRow, totalPage=$totalPage, pageNumber=$pageNumber, pageSize=$pageSize, list=$list)"
    }

}
