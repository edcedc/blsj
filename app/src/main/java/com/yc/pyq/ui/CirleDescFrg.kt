package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.yc.pyq.R
import com.yc.pyq.adapter.CirlceAdapter
import com.yc.pyq.adapter.CommentAdapter
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.event.CirlePraiseInEvent
import com.yc.pyq.mvp.impl.CirleDescContract
import com.yc.pyq.mvp.presenter.CirleDescPresenter
import com.yc.pyq.ui.bottom.CommentBottomFrg
import kotlinx.android.synthetic.main.f_cirle_desc.*
import kotlinx.android.synthetic.main.include_comment.*
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/29
 * Time: 16:15
 */
class CirleDescFrg : BaseFragment(), CirleDescContract.View, View.OnClickListener{

     val mPresenter by lazy { CirleDescPresenter() }

     val listCirlceBean = ArrayList<DataBean>()

     val listBean = ArrayList<DataBean>()

     val cirlceAdapter by lazy { activity?.let { CirlceAdapter(it,this, listCirlceBean) } }

     val adapter by lazy { activity?.let { CommentAdapter(it,listBean) } }

     var commentBottomFrg: CommentBottomFrg? = null

    lateinit var bean: DataBean

    var id: String? = null

    fun newInstance(): CirleDescFrg {
        val args = Bundle()
        val fragment = CirleDescFrg()
        fragment.arguments = args
        return fragment
    }

    override fun getLayoutId(): Int = R.layout.f_cirle_desc

    override fun initParms(bundle: Bundle) {
        bean = Gson().fromJson(bundle.getString("bean"), DataBean::class.java)
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.circle_desc))
        mPresenter.init(this, act)
        commentBottomFrg = CommentBottomFrg()
        et_text.setOnClickListener(this)
        setRecyclerViewType(rv_top)
//        rv_top.addItemDecoration(LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 2))
        rv_top.adapter = cirlceAdapter
        (rv_top.getItemAnimator() as DefaultItemAnimator).supportsChangeAnimations = false // 取消动画效果
//        adapter?.setHasStableIds (true)
        cirlceAdapter?.setOnClickListener(object : CirlceAdapter.OnClickListener{
            override fun onZan(position: Int, circleId: String, isPraise: Int) {
                LogUtils.e(isPraise)
                mPresenter.onSsavePraise(0, circleId, isPraise)
            }
        })

        setRecyclerViewType(recyclerView)
//        recyclerView.addItemDecoration(LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 2))
        recyclerView.adapter = adapter

        showUiLoading()
        id = bean.id
        listCirlceBean.add(bean)
        cirlceAdapter?.notifyDataSetChanged()

        refreshLayout.autoRefresh()//第一次进入触发自动刷新，演示效果
        refreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pagerNumber = 1
                mPresenter.onRequest(pagerNumber, id)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pagerNumber += 1
                mPresenter.onRequest(pagerNumber, id)
            }
        })

        commentBottomFrg?.setOnCommentListener(object : CommentBottomFrg.onCommentListener{

            override fun onFirstComment(position: Int, id: String?, text: String?, type: Int) {
                mPresenter.onFirstSend(position, id, text)
            }

            override fun onSecondComment( position: Int, id: String?, userId: String?, byReplyUserId: String?, parentId: String?, text: String?, type: Int) {
                mPresenter.onSecondComment(position, id, userId, byReplyUserId, parentId, text)
            }

        })

        adapter?.setOnClickListener(object : CommentAdapter.onClickListener{
            override fun onClick(position: Int,id: String?,userId: String?,byReplyUserId: String?,parentId: String?) {
                LogUtils.e(id, userId, byReplyUserId, parentId)
                commentBottomFrg?.onSecondComment(position, id, userId, byReplyUserId, parentId, 2)
                commentBottomFrg?.show(childFragmentManager, "dialog")
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


    override fun onClick(p0: View?) {
       when(p0?.id){
            R.id.et_text -> {
                commentBottomFrg?.onFirstComment(0, id as String, 1)
                commentBottomFrg?.show(childFragmentManager, "dialog")
            }
       }
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

    override fun setSavePraise(position: Int, praise: Int) {
        var  bean = listCirlceBean.get(position)
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
        cirlceAdapter?.notifyDataSetChanged()
        EventBus.getDefault().post(CirlePraiseInEvent(praise, bean?.id))
    }

    override fun setFirstSend(position: Int, data: DataBean?) {
        listBean.add(0, data as DataBean)
        adapter?.notifyDataSetChanged()
//        adapter?.notifyItemInserted(0)
    }

}

