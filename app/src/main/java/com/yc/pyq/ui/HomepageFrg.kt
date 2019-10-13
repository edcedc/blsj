package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.StringUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.yc.pyq.R
import com.yc.pyq.adapter.HomepageAdapter
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.controller.CloudApi
import com.yc.pyq.mvp.impl.HomepageContract
import com.yc.pyq.mvp.presenter.HomepagePresenter
import com.yc.pyq.weight.GlideLoadingUtils
import kotlinx.android.synthetic.main.f_homepage.*
import kotlinx.android.synthetic.main.f_one.recyclerView
import kotlinx.android.synthetic.main.f_one.refreshLayout
import java.util.ArrayList

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/8
 * Time: 14:20
 *  个人主页
 */
class HomepageFrg : BaseFragment(), HomepageContract.View {

    val mPresenter by lazy { HomepagePresenter() }

    var byUserId: String? = null

    val listBean = ArrayList<DataBean>()

    var isFollow: Int = 0

   val adapter by lazy { act?.let { HomepageAdapter(it, listBean) } }


    override fun getLayoutId(): Int = R.layout.f_homepage

    override fun initParms(bundle: Bundle) {
        byUserId = bundle.getString("byUserId")
    }

    override fun initView(rootView: View) {
        mPresenter?.init(this, act)
        mPresenter.onUserInformation(byUserId)
        setRecyclerViewType(recyclerView = recyclerView)
        recyclerView.adapter = adapter

        refreshLayout.autoRefresh()//第一次进入触发自动刷新，演示效果
        refreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                pagerNumber = 1
                mPresenter.onRequest(pagerNumber, byUserId)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pagerNumber += 1
                mPresenter.onRequest(pagerNumber, byUserId)
            }
        })
    }

    override fun setRefreshLayoutMode(totalRow: Int) {
        super.setRefreshLayoutMode(listBean.size, totalRow, refreshLayout)
    }

    override fun setData(objects: Object) {
        val list = objects as List<DataBean>
        if (pagerNumber == 1){
            listBean.clear()
        }
        listBean.addAll(list)
        adapter?.notifyDataSetChanged()
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }


    override fun setUser(data: DataBean?) {
        isFollow = data?.isFollow!!
        if (!StringUtils.isEmpty(byUserId) &&  isFollow == 0){
            setTitle(getString(R.string.user_page), "关注")
        }else if (!StringUtils.isEmpty(byUserId) && isFollow == 1){
            setTitle(getString(R.string.user_page), "已关注")
        }else{
            setTitle(getString(R.string.me_page))
        }
        val userExtend = data?.userExtend
        GlideLoadingUtils.loadRounded(act, CloudApi.SERVLET_IMG_URL + userExtend?.head, iv_head)
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + userExtend?.background, iv_img)
        tv_name.text = userExtend?.nick
    }

    override fun setOnRightClickListener() {
        super.setOnRightClickListener()
        mPresenter.onFollow(isFollow, byUserId)
    }

    override fun setFollow(desc: String?) {
        if (desc.equals("已取消关注")){
            setTitle(getString(R.string.user_page), "关注")
        }else{
            setTitle(getString(R.string.user_page), "已关注")
        }
    }

}