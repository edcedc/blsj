package com.yc.pyq.ui.bottom

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.DialogFragment
import com.blankj.utilcode.util.StringUtils
import com.yc.pyq.R
import com.yc.pyq.base.BaseBottomSheetFrg

import android.content.Context.INPUT_METHOD_SERVICE
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/29
 * Time: 14:23
 */
class CommentBottomFrg : BaseBottomSheetFrg(), TextView.OnEditorActionListener, View.OnClickListener {

    private var etText: AppCompatEditText? = null
    private var type = 1

    private var listener: onCommentListener? = null

    override fun bindLayout(): Int {
        return R.layout.include_comment
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetEdit)
    }

    override fun initView(view: View) {
        view.findViewById<AppCompatTextView>(R.id.tv_send).setOnClickListener(this)
        etText = view.findViewById(R.id.et_text)
        etText?.setOnEditorActionListener(this)
        etText?.isFocusable = true
        etText?.isFocusableInTouchMode = true
        etText?.isCursorVisible = true
        etText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                /*if (editable.length() > 100){
                    showToast("字数过长可能无法显示");
                    return;
                }*/
            }
        })
        Handler().postDelayed({ showInput(etText!!) }, 200)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_send -> {
                val s = etText?.text.toString()
                if (StringUtils.isEmpty(s)) {
                    dismiss()
                } else {
                    if (listener != null && type == 1) {
                        listener!!.onFirstComment(position, id, s, type)
                        dismiss()
                    } else if (listener != null && type == 2) {
                        listener!!.onSecondComment(position, id, replyUserId, byReplyUserId, parentId, s, type);
                        dismiss()
                    }
                }
            }
        }
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    fun showInput(et: EditText) {
        et.requestFocus()
        val imm = act.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroy() {
        super.onDestroy()
        close(false)
        etText!!.setText("")
        etText!!.hint = resources.getText(R.string.yyc_5)
        type = 1
    }

    override fun initParms(bundle: Bundle) {

    }

     var id: String?= null
     var text: String?= null
     var replyUserId: String?= null
     var byReplyUserId: String?= null
     var parentId: String?= null
    var position: Int = 0

    fun setOnCommentListener(listener: onCommentListener) {
        this.listener = listener
    }
    interface onCommentListener {
        fun onFirstComment(position: Int, id: String?= null, text: String?= null, type: Int)
        fun onSecondComment(position: Int, id: String?= null, userId: String?= null, byReplyUserId: String?= null, parentId: String?= null,text: String?= null, type: Int)
    }

    fun onSecondComment(position: Int, id: String?= null, replyUserId: String?= null,byReplyUserId: String?= null, parentId: String?= null, type: Int) {
        this.position = position
        this.id = id
        this.replyUserId = replyUserId
        this.byReplyUserId = byReplyUserId
        this.parentId = parentId
        this.type = type
    }

    fun onFirstComment(position: Int, id: String?= null, type: Int) {
        this.position = position
        this.id = id
        this.type = type
    }

    override fun onEditorAction(textView: TextView?, i: Int, keyEvent: KeyEvent?): Boolean {
        when (i) {
            EditorInfo.IME_ACTION_SEND -> {
                val s = textView?.text.toString()
                if (StringUtils.isEmpty(s)) {
                    dismiss()
                } else {
                    if (listener != null && type == 1) {
                        listener!!.onFirstComment(position, id, s, type)
                        dismiss()
                    } else if (listener != null && type == 2) {
                        //                        listener.onSecondComment(position, infoId, discussId, s, pUserId);
                        dismiss()
                    }
                }
            }
        }
        return true
    }


}
