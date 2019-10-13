package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.yc.pyq.R
import com.yc.pyq.adapter.CirlceAdapter
import com.yc.pyq.adapter.ProblemsAdapter
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.mvp.impl.ProblemsContract
import com.yc.pyq.mvp.presenter.OnePresenter
import com.yc.pyq.mvp.presenter.ProblemsPresenter
import com.yc.pyq.weight.LinearDividerItemDecoration
import kotlinx.android.synthetic.main.b_title_recycler.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 17:32
 *  常见问题
 */
class ProblemsFrg : BaseFragment(), ProblemsContract.View {

    val mPresenter by lazy { ProblemsPresenter() }

    val listBean = ArrayList<DataBean>()

    val adapter by lazy { activity?.let { ProblemsAdapter(it, this, listBean) } }

    override fun getLayoutId(): Int = R.layout.b_title_recycler

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.common_problems))
        mPresenter.init(this, act)
        setRecyclerViewType(recyclerView = recyclerView)
        recyclerView.addItemDecoration(LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 10))
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
    }


    override fun setData(objects: Object) {
        val list = objects as List<DataBean>
        if (pagerNumber == 1) {
            listBean.clear()
        }
        listBean.addAll(list)
        adapter?.notifyDataSetChanged()
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    override fun hideLoading() {
        super.hideLoading()
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    override fun setRefreshLayoutMode(totalRow: Int) {
        super.setRefreshLayoutMode(listBean.size, totalRow, refreshLayout)
    }

}