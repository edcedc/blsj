package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.tencent.bugly.proguard.ad
import com.yc.pyq.R
import com.yc.pyq.adapter.CirlceAdapter
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.mvp.impl.OneContract
import com.yc.pyq.mvp.presenter.OnePresenter
import com.yc.pyq.weight.GlideImageLoader
import com.yc.pyq.weight.LinearDividerItemDecoration
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.transformer.DefaultTransformer
import kotlinx.android.synthetic.main.f_one.*
import java.util.ArrayList



/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/25
 * Time: 11:09
 */
class OneFrg : BaseFragment(), OneContract.View, OnBannerListener, View.OnClickListener{

    fun newInstance(): OneFrg {
        val args = Bundle()
        val fragment = OneFrg()
        fragment.arguments = args
        return fragment
    }

     val mPresenter by lazy { OnePresenter() }

     val listBean = ArrayList<DataBean>()

     val adapter by lazy { activity?.let { CirlceAdapter(it,this, listBean) } }

     val listBannerBean = ArrayList<DataBean>()

    override fun getLayoutId(): Int = R.layout.f_one

    override fun initParms(bundle: Bundle) {

    }

    override fun initView(rootView: View) {
        setTitleCenter(getString(R.string.yyc_4), R.mipmap.b01)
        setSwipeBackEnable(false)
        mPresenter.init(this, act)
        mPresenter.onBanner()

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
        refreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
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
        if (pagerNumber == 1){
            listBean.clear()
        }
        listBean.addAll(list)
        adapter?.notifyDataSetChanged()
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }


    override fun setBanner(list: List<DataBean>) {
        listBannerBean.clear()
        listBannerBean.addAll(list)
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
        val list1 = ArrayList<String>()
        val imgs = ArrayList<String>()
        for (bean in listBannerBean) {
            imgs.add(bean.image.toString())
            list1.add(bean.name.toString())
        }
        banner.setImages(listBannerBean)
            .setBannerTitles(list1)
            .setImageLoader(GlideImageLoader())
            .setOnBannerListener(this)
            .setBannerAnimation(DefaultTransformer::class.java)
            .start()
    }

    override fun OnBannerClick(position: Int) {
        val bean = listBannerBean[position]

    }

    override fun setSavePraise(position: Int, praise: Int) {
        val bean = listBean[position]
        var isPraise = bean.isPraise
        if (isPraise == null){
            isPraise = DataBean()
            isPraise.state = praise
            bean.isPraise = isPraise
            if (praise == 0){
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

    override fun hideLoading() {
        super.hideLoading()
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    override fun setRefreshLayoutMode(totalRow: Int) {
        super.setRefreshLayoutMode(listBean.size, totalRow, refreshLayout)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){

        }
    }

    override fun setOnRightClickListener() {
        super.setOnRightClickListener()
        UIHelper.startReleaseAct()
    }

}
