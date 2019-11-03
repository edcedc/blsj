package com.yc.pyq.bean


import java.io.Serializable

/**
 * Created by yc on 2017/8/17.
 */

class DataBean : Serializable {

    var name: String? = null
    var reply_user_id: String? = null
    var content: String? = null
    var reply_nick: String? = null
    var head: String? = null
    var emoji_content: String? = null
    var title: String? = null
    var user_id: String? = null
    var parent_id: String? = null
    var circle_id: String? = null
    var type: Int = 0
    var position: Int = 0
    var nick: String? = null
    var location: String? = null
    var brief: String? = null
    var signature: String? = null
    var url: String? = null
    var byUserId: String? = null
    var isFollow: Int = 0
    var create_time: String? = null
    var background: String? = null
    var emoji_remark: String? = null
    var remark: String? = null
    var image: String? = null
    var video_image: String? = null
    var video_url: String? = null
    var browse: Int? = null
    var tread: Int = 0
    var comment_number: Int = 0
    var praise: Int = 0
    var state: Int = 0
    var totalRow: Int = 0
    var id: String? = null
    val imageList: List<DataBean>? = null
    var isPraise: DataBean? = null
    var adv: DataBean? = null
    var userExtend: DataBean? = null
    var pageCommentsLowerLevel: DataBean? = null
    var introduction: String? = null

}