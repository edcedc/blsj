package com.yc.pyq.event

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/11/2
 * Time: 18:31
 */
class CirlePraiseInEvent{

    var praise = 0
    var id: String? = null

    constructor(praise: Int, id: String?) {
        this.praise = praise
        this.id = id
    }
}
