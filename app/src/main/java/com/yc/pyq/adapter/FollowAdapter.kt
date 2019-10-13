package com.yc.pyq.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.hazz.kotlinmvp.view.recyclerview.adapter.BaseRecyclerviewAdapter
import com.yc.pyq.R
import com.yc.pyq.adapter.base.ViewHolder
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.controller.CloudApi
import com.yc.pyq.weight.GlideLoadingUtils

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 0:14
 */
class FollowAdapter (act: Context, root: BaseFragment, listBean: List<DataBean>) : BaseRecyclerviewAdapter<DataBean>(act, listBean as ArrayList<DataBean>) {

    override fun onCreateViewHolde(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.i_follow, parent, false))
    }

    override fun onBindViewHolde(viewHolder: ViewHolder, position: Int) {
        val bean = listBean[position]
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.head, viewHolder.getView<AppCompatImageView>(R.id.iv_head))
        viewHolder.setText(R.id.tv_name, bean.nick)
        viewHolder.getView<AppCompatTextView>(R.id.tv_text).setOnClickListener { listener?.onClick(position, bean.user_id) }
    }

     var listener: OnClickListener? = null

    interface OnClickListener {
        fun onClick(position: Int, userId: String?)
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }


}