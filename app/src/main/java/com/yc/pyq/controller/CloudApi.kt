package com.yc.pyq.controller

/**
 * 作者：yc on 2018/6/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

class CloudApi private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object{

//        val url =
//            "10.0.1.141:8080"
//
//        val SERVLET_URL = "http://" +
//                url + "/forward/"

//        http://jj123.nat300.top/adv_chain/api/user/update

        private val url =
            "47.100.199.115"

        val SERVLET_IMG_URL = "http://" +
                url + "/adv_chain/"

        val SERVLET_URL = SERVLET_IMG_URL + "api/"

        private var TEST_URL = "" //测试

        private val TAG = "CloudApi"

    }


}