package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.yc.pyq.R
import com.yc.pyq.adapter.FollowAdapter
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.mvp.impl.FollowContract
import com.yc.pyq.mvp.presenter.FollowPresenter
import com.yc.pyq.utils.PopupWindowTool
import com.yc.pyq.weight.LinearDividerItemDecoration
import kotlinx.android.synthetic.main.b_title_recycler.*
import java.util.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/12
 * Time: 23:27
 *  我关注的人
 */
class FollowFrg :BaseFragment(), FollowContract.View{

    val mPresenter by lazy { FollowPresenter() }

    val listBean = ArrayList<DataBean>()

    val adapter by lazy { activity?.let { FollowAdapter(it,this, listBean) } }

    override fun getLayoutId(): Int = R.layout.b_title_recycler

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.me_follow))
        mPresenter.init(this, act)
        setRecyclerViewType(recyclerView = recyclerView)
        recyclerView.addItemDecoration(LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 2))
        recyclerView.adapter = adapter
        refreshLayout.autoRefresh()//第一次进入触发自动刷新，演示效果
        refreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                pagerNumber = 1
                mPresenter.onRequest(pagerNumber)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pagerNumber += 1
                mPresenter.onRequest(pagerNumber)
            }
        })
        adapter?.setOnClickListener(object : FollowAdapter.OnClickListener{
            override fun onClick(position: Int, userId: String?) {
                PopupWindowTool.showDialog(act) .asConfirm("确定取消关注吗？", "","取消", "确定",{
                            mPresenter.onFollow(position, userId)
                        }, null, false  ) .bindLayout(R.layout.p_dialog) .show()
            }
        })
    }

    override fun hideLoading() {
        super.hideLoading()
        if (refreshLayout != null){
            refreshLayout.finishRefresh()
            refreshLayout.finishLoadMore()
        }
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

    override fun setFollow(position: Int) {
        listBean.removeAt(position)
        adapter?.notifyItemRemoved(position)
        adapter?.notifyItemChanged(position)
    }

}