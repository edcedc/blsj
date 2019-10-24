package com.yc.pyq.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.yc.mema.event.CameraInEvent
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.base.User
import com.yc.pyq.controller.CloudApi
import com.yc.pyq.mvp.impl.UserUpdateContract
import com.yc.pyq.mvp.presenter.UserUptatePresenter
import com.yc.pyq.utils.DatePickerUtils
import com.yc.pyq.utils.PictureSelectorTool
import com.yc.pyq.utils.PopupWindowTool
import com.yc.pyq.weight.GlideLoadingUtils
import kotlinx.android.synthetic.main.f_update_user.*
import kotlinx.android.synthetic.main.f_update_user.iv_head
import kotlinx.android.synthetic.main.f_update_user.iv_img
import kotlinx.android.synthetic.main.f_update_user.tv_name
import kotlinx.android.synthetic.main.f_update_user.tv_nick
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 12:39
 *  更新个人信息
 */
class UpdateUserFrg : BaseFragment(), UserUpdateContract.View, View.OnClickListener {

    val mPresenter by lazy { UserUptatePresenter() }

    val localMediaList = ArrayList<LocalMedia>()

    var type: Int = 0

    override fun getLayoutId(): Int = R.layout.f_update_user

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.information), getString(R.string.sure1))
        mPresenter.init(this, act)
        iv_img.setOnClickListener(this)
        iv_head.setOnClickListener(this)
        tv_address.setOnClickListener(this)
        EventBus.getDefault().register(this)
        val userObj = User.getInstance().userObj
        GlideLoadingUtils.loadRounded(act, CloudApi.SERVLET_IMG_URL + userObj.optString("head"), iv_head)
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + userObj.optString("background"), iv_img)
        tv_name.text = userObj.optString("nick")
        et_name.setText(userObj.optString("nick"))
        tv_nick.text = userObj.optString("signature")
        et_nick.setText(userObj.optString("signature"))
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_img -> {
                PopupWindowTool.showDialog(act).asConfirm("是否修改背景图?", "", "取消", "确定", {
                    type = 1
                    act?.let {
                        PictureSelectorTool.PictureSelectorImage(
                            it,
                            localMediaList as List<LocalMedia>,
                            1,
                            PictureConfig.CHOOSE_REQUEST
                        )
                    }
                }, null, false).bindLayout(R.layout.p_dialog).show()
            }
            R.id.iv_head -> {
                PopupWindowTool.showDialog(act).asConfirm("是否修改头像？", "", "取消", "确定", {
                    type = 2
                    act?.let {
                        PictureSelectorTool.PictureSelectorImage(
                            it,
                            localMediaList as List<LocalMedia>,
                            1,
                            PictureConfig.CHOOSE_REQUEST
                        )
                    }
                }, null, false).bindLayout(R.layout.p_dialog).show()
            }
            R.id.tv_address -> {
                DatePickerUtils.onAddressPicker(act, { add -> tv_address.setText(add) })
            }
        }
    }

    @Subscribe
    fun onMainCameraInEvent(event: CameraInEvent) {
        localMediaList.clear()
        localMediaList.addAll(PictureSelector.obtainMultipleResult(event.`object` as Intent))
        val path = localMediaList[0].compressPath
        if (type == 1) {
            Glide.with(this).load(path).into(iv_img)
            mPresenter.onUptate(localMediaList, null, null, null, tv_address.text.toString())
        } else {
            Glide.with(this).load(path).into(iv_head)
            mPresenter.onUptate(null, localMediaList, null, null, tv_address.text.toString())
        }
    }

    override fun onSucees() {

    }

    override fun setOnRightClickListener() {
        super.setOnRightClickListener()
        mPresenter.onUptate(null, null, et_name.text.toString(), et_nick.text.toString(), tv_address.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}