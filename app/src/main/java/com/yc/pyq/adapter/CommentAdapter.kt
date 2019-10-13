package com.yc.pyq.adapter

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.blankj.utilcode.util.EncodeUtils
import com.hazz.kotlinmvp.view.recyclerview.adapter.BaseRecyclerviewAdapter
import com.yc.pyq.R
import com.yc.pyq.adapter.base.ViewHolder
import com.yc.pyq.base.User
import com.yc.pyq.bean.DataBean

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/29
 * Time: 18:38
 */
class CommentAdapter (act: Context, listBean: List<DataBean>) : BaseRecyclerviewAdapter<DataBean>(act, listBean as ArrayList<DataBean>) {

    override fun onCreateViewHolde(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.i_comment, parent, false))
    }

    override fun onBindViewHolde(viewHolder: ViewHolder, position: Int) {
        val bean = listBean[position]
        val tv_text = viewHolder.getView<AppCompatTextView>(R.id.tv_text)

        val nick = bean.nick
        val replyNick = bean.reply_nick
        val content =  String(EncodeUtils.base64Decode(bean.emoji_content))
        val replyUserId = bean.reply_user_id
        if (replyUserId != null){
            val hText = SpannableString(nick + "回复" + replyNick + "：" + content)
            hText.setSpan(ForegroundColorSpan(Color.parseColor("#999999")), nick!!.length, nick.length + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            hText.setSpan(ForegroundColorSpan(Color.parseColor("#999999")), hText.length - content!!.length, hText.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            tv_text.text = hText
        }else{
            val hText = SpannableString(nick + "回复：" + content)
            hText.setSpan(ForegroundColorSpan(Color.parseColor("#999999")), hText.length - content!!.length, hText.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            tv_text.text = hText
        }

        viewHolder.itemView.setOnClickListener({
            listener?.onClick(position, bean.circle_id, User.getInstance().userId, bean.user_id, bean.id)
        })

    }

    private var listener: onClickListener? = null

    fun setOnClickListener(listener: onClickListener) {
        this.listener = listener
    }
    interface onClickListener {
        fun onClick(position: Int, id: String?= null, userId: String?= null, byReplyUserId: String?= null, parentId: String?= null)
    }

}