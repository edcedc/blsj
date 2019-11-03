package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.blankj.utilcode.util.LogUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.yc.pyq.R
import com.yc.pyq.adapter.CirlceAdapter
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.event.CirlePraiseInEvent
import com.yc.pyq.mvp.impl.OneContract
import com.yc.pyq.mvp.presenter.OnePresenter
import com.yc.pyq.weight.LinearDividerItemDecoration
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.f_one.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/25
 * Time: 11:09
 */
class TwoFrg : BaseFragment(), OneContract.View, OnBannerListener, View.OnClickListener{

     val mPresenter by lazy { OnePresenter() }

     val listBean = ArrayList<DataBean>()

     val adapter by lazy { activity?.let { CirlceAdapter(it,this, listBean) } }

    override fun initParms(bundle: Bundle) {

    }

    override fun getLayoutId(): Int = R.layout.f_one

    override fun initView(rootView: View) {
        setTitleCenter(getString(R.string.me_follow))
        setSwipeBackEnable(false)
        mPresenter.init(this, act)
        banner.visibility = View.GONE
        setRecyclerViewType(recyclerView = recyclerView)
        recyclerView.addItemDecoration(LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 2))
        recyclerView.adapter = adapter
        (recyclerView.getItemAnimator() as DefaultItemAnimator).supportsChangeAnimations = false // 取消动画效果
//        adapter?.setHasStableIds (true)
        adapter?.setOnClickListener(object : CirlceAdapter.OnClickListener{
            override fun onZan(position: Int, circleId: String, isPraise: Int) {
                mPresenter.onSsavePraise(position, circleId, isPraise)
            }
        })

        refreshLayout.autoRefresh()//第一次进入触发自动刷新，演示效果
        refreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                pagerNumber = 1
                mPresenter.onPageFocus(pagerNumber)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pagerNumber += 1
                mPresenter.onPageFocus(pagerNumber)
            }
        })
        EventBus.getDefault().register(this)
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

    override fun onClick(p0: View?) {
    }

    override fun setBanner(list: List<DataBean>) {
    }

    override fun setSavePraise(position: Int, praise: Int) {
        val bean = listBean[position]
        var isPraise = bean.isPraise
        if (isPraise == null){
            isPraise = DataBean()
            isPraise.state = 2
            bean.isPraise = isPraise
            if (praise == 1){
                bean.praise += 1
            }else{
                bean.tread += 1
            }
        }else{
            if (praise == 1){
                bean.praise = if (bean.praise == 0) 0 else bean.praise - 1
                bean.tread += 1
            }else{
                bean.praise += 1
                bean.tread = if (bean.tread == 0) 0 else bean.tread - 1
            }
        }
        isPraise?.state = praise
        adapter?.notifyItemChanged(position)
    }

    override fun OnBannerClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun hideLoading() {
        super.hideLoading()
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    override fun setRefreshLayoutMode(totalRow: Int) {
        super.setRefreshLayoutMode(listBean.size, totalRow, refreshLayout)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onMainCirlePraiseInEvent(event: CirlePraiseInEvent) {
        for (i in 0..listBean.size) {
            var  bean = listBean[i]
            if (bean?.id.equals(event.id)){
                setSavePraise(i, event.praise)
                break
            }
        }
    }

}
