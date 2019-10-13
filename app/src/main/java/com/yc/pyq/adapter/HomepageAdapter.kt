package com.yc.pyq.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.Group
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.hazz.kotlinmvp.view.recyclerview.adapter.BaseRecyclerviewAdapter
import com.yc.pyq.R
import com.yc.pyq.adapter.base.ViewHolder
import com.yc.pyq.base.User
import com.yc.pyq.bean.DataBean
import com.yc.pyq.controller.CloudApi
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.utils.TimeUtil
import com.yc.pyq.weight.GlideLoadingUtils

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/11
 * Time: 21:52
 */
class HomepageAdapter(act: Context,  listBean: List<DataBean>) :BaseRecyclerviewAdapter<DataBean>(act, listBean as ArrayList<DataBean>){

    override fun onCreateViewHolde(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.i_homepage, parent, false))
    }

    override fun onBindViewHolde(viewHolder: ViewHolder, position: Int) {
        val bean = listBean[position]
        val ly_url = viewHolder.getView<LinearLayout>(R.id.ly_url)
        val tv_title = viewHolder.getView<AppCompatTextView>(R.id.tv_title)
        val tv_content = viewHolder.getView<AppCompatTextView>(R.id.tv_content)
        val iv_play = viewHolder.getView<AppCompatImageView>(R.id.iv_play)
        val iv_img = viewHolder.getView<AppCompatImageView>(R.id.iv_img)
        val gp_rele = viewHolder.getView<Group>(R.id.gp_rele)
        viewHolder.setText(R.id.tv_time, TimeUtil.getTimeFormatText(bean.create_time))
        val remark = bean.remark
        if (!StringUtils.isEmpty(remark)){
            tv_title.text = remark
            tv_title.visibility = View.VISIBLE
        }else{
            tv_title.visibility = View.GONE
        }
        when(bean.type){//1图文链接，2相片，3视频，4纯文字
            1 ->{
                iv_play.visibility = View.GONE
                iv_img.visibility = View.GONE
                tv_content.visibility = View.GONE
                ly_url.visibility = View.VISIBLE
                GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.image, viewHolder.getView<AppCompatImageView>(R.id.iv_url_img))
                viewHolder.setText(R.id.tv_url_title, bean.title)
            }
            2 ->{
                iv_play.visibility = View.GONE
                iv_img.visibility = View.VISIBLE
                tv_content.visibility = View.VISIBLE
                ly_url.visibility = View.GONE
                val imageList = bean.imageList
                if (imageList != null && imageList.size != 0){
                    GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + imageList[0].image, iv_img)
                    tv_content.text = "共" + imageList.size + "张"
                }
            }
            3 ->{
                iv_play.visibility = View.VISIBLE
                iv_img.visibility = View.VISIBLE
                tv_content.visibility = View.VISIBLE
                ly_url.visibility = View.GONE
                GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.video_image, iv_img)
            }
            4 ->{
                iv_play.visibility = View.GONE
                iv_img.visibility = View.GONE
                tv_content.visibility = View.GONE
                ly_url.visibility = View.GONE
            }
        }

        if (bean.user_id.equals(User.getInstance().userId) && position == 0){
            gp_rele.visibility = View.VISIBLE
        }else{
            gp_rele.visibility = View.GONE
        }
        viewHolder.getView<FrameLayout>(R.id.fy_release).setOnClickListener { UIHelper.startReleaseAct() }

        viewHolder.itemView.setOnClickListener({UIHelper.startCirleDescAct(bean)})

    }

}