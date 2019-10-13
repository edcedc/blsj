package com.yc.pyq.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.hazz.kotlinmvp.view.recyclerview.adapter.BaseRecyclerviewAdapter
import com.yc.pyq.R
import com.yc.pyq.adapter.base.ViewHolder
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.base.User
import com.yc.pyq.bean.DataBean
import com.yc.pyq.controller.CloudApi
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.ui.act.HtmlAct
import com.yc.pyq.utils.TimeUtil
import com.yc.pyq.weight.GlideLoadingUtils
import com.yc.pyq.weight.NineGridTestLayout
import com.yc.pyq.weight.RoundImageView

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/25
 * Time: 15:08
 */
class CirlceAdapter(act: Context, root: BaseFragment, listBean: List<DataBean>) : BaseRecyclerviewAdapter<DataBean>(act, listBean as ArrayList<DataBean>) {

    var root : BaseFragment = root

    override fun onCreateViewHolde(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.i_cirlce, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolde(viewHolder: ViewHolder, position: Int) {
        val bean = listBean[position]
        var tv_content = viewHolder.getView<AppCompatTextView>(R.id.tv_content)
        var ly_url = viewHolder.getView<LinearLayout>(R.id.ly_url)
        var fy_video = viewHolder.getView<FrameLayout>(R.id.fy_video)
        var tv_zan = viewHolder.getView<AppCompatTextView>(R.id.tv_zan)
        var tv_cai = viewHolder.getView<AppCompatTextView>(R.id.tv_cai)
        var ly_adv = viewHolder.getView<ConstraintLayout>(R.id.ly_adv)
        val nineGridTestLayout = viewHolder.getView<NineGridTestLayout>(R.id.layout_nine_grid)
        nineGridTestLayout.setIsShowAll(true)
        viewHolder.setText(R.id.tv_name, bean.nick as String)
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.head, viewHolder.getView(R.id.iv_head), true)
        val remark = bean.emoji_remark
        tv_content.visibility = (if (StringUtils.isEmpty(remark)) View.GONE else View.VISIBLE)
        tv_content.text =  String(EncodeUtils.base64Decode(remark))
        viewHolder.setText(R.id.tv_comment, bean.comment_number)
        viewHolder.setText(R.id.tv_time, TimeUtil.getTimeFormatText(bean.create_time))
        tv_zan.text = bean.praise.toString()
        tv_cai.text = bean.tread.toString()

        when(bean.type){
            1 ->{
                ly_url.visibility = View.VISIBLE
                GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.image, viewHolder.getView<AppCompatImageView>(R.id.iv_img))
                viewHolder.setText(R.id.tv_title, bean.title)
                fy_video.visibility = View.GONE
                nineGridTestLayout.visibility = View.GONE
            }
            2 ->{
                nineGridTestLayout.visibility = View.VISIBLE
                val imageList = bean.imageList
                if (imageList != null && imageList.size != 0){
                    val list = ArrayList<String>()
                    for (images in imageList) {
                        list.add(images.image as String)
                    }
                    nineGridTestLayout.setUrlList(list);
                }else{
                    nineGridTestLayout.visibility = View.GONE
                }
                ly_url.visibility = View.GONE
                fy_video.visibility = View.GONE
            }
            3 ->{
                fy_video.visibility = View.VISIBLE
                GlideLoadingUtils.loadMeasuring(act, CloudApi.SERVLET_IMG_URL + bean.video_image, viewHolder.getView(R.id.iv_video_img))
                viewHolder.getView<AppCompatImageView>(R.id.iv_play).visibility = View.VISIBLE
                ly_url.visibility = View.GONE
                nineGridTestLayout.visibility = View.GONE
            }
            4 ->{
                ly_url.visibility = View.GONE
                fy_video.visibility = View.GONE
                nineGridTestLayout.visibility = View.GONE
            }
        }

        viewHolder.getView<RoundImageView>(R.id.iv_head).setOnClickListener {
            UIHelper.startHomepageFrg(root, if (bean.user_id.equals(User.getInstance().userId)) null else bean.user_id)
        }

        ly_url.setOnClickListener {
            UIHelper.startHtmlAct(HtmlAct.DESC, bean.url)
        }
        fy_video.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            LogUtils.e(CloudApi.SERVLET_IMG_URL + bean.video_url)
            val data = Uri.parse(CloudApi.SERVLET_IMG_URL + bean.video_url)
            intent.setDataAndType(data, "video/mp4")
            act.startActivity(intent)
        }

        val isPraise = bean.isPraise
        if (isPraise == null){
            tv_zan.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.b10,null), null, null, null)
            tv_cai.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.b06,null), null, null, null)
        }else if (isPraise.state == 0){
            tv_zan.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.b05,null), null, null, null)
            tv_cai.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.b06,null), null, null, null)
        }else if (isPraise.state == 1){
            tv_zan.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.b10,null), null, null, null)
            tv_cai.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.b11,null), null, null, null)
        }
        tv_zan.setOnClickListener({
            if (isPraise != null && isPraise.state == 0)return@setOnClickListener
            listener?.onZan(position, bean.id as String, 0)
        })
        tv_cai.setOnClickListener({
            if (isPraise != null && isPraise.state == 1)return@setOnClickListener
            listener?.onZan(position, bean.id as String, 1)
        })
        viewHolder.itemView.setOnClickListener({UIHelper.startCirleDescAct(bean)})

        val adv = bean.adv
        if (adv != null){
            ly_adv.visibility = View.VISIBLE
            GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + adv.head, viewHolder.getView<AppCompatImageView>(R.id.iv_adv_head))
            viewHolder.setText(R.id.tv_adv_name, adv.name)
            viewHolder.setText(R.id.tv_adv_content, adv.content)
            GlideLoadingUtils.loadMeasuring(act, CloudApi.SERVLET_IMG_URL + adv.image, viewHolder.getView(R.id.iv_adv))
            ly_adv.setOnClickListener {
                UIHelper.startHtmlAct(HtmlAct.ADV, adv.url, adv.name)
            }
        }else{
            ly_adv.visibility = View.GONE
        }
    }

    private var listener: OnClickListener? = null

    interface OnClickListener {
        fun onZan(position: Int, circleId: String, isPraise: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }


}
