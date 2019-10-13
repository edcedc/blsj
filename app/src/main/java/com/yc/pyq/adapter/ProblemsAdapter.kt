package com.yc.pyq.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hazz.kotlinmvp.view.recyclerview.adapter.BaseRecyclerviewAdapter
import com.yc.pyq.R
import com.yc.pyq.adapter.base.ViewHolder
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.bean.DataBean
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.ui.act.HtmlAct

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 17:40
 */
class ProblemsAdapter (act: Context, root: BaseFragment, listBean: List<DataBean>) : BaseRecyclerviewAdapter<DataBean>(act, listBean as ArrayList<DataBean>){

    override fun onCreateViewHolde(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.i_problems, parent, false))
    }

    override fun onBindViewHolde(viewHolder: ViewHolder, position: Int) {
        val bean = listBean[position]
        viewHolder.setText(R.id.tv_title, "(" + (position + 1) + ")" + bean.title)
        var content = bean.content as String
        content = content.replace("<p>", "")
        viewHolder.setText(R.id.tv_content, content)
        viewHolder.itemView.setOnClickListener { UIHelper.startHtmlAct(HtmlAct.PROBLEMS, bean.content) }
    }

}