package com.yc.pyq.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.yc.mema.event.CameraInEvent
import com.yc.pyq.R
import com.yc.pyq.adapter.ImageAdapter
import com.yc.pyq.base.BaseActivity
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.controller.UIHelper
import com.yc.pyq.mvp.impl.UserUpdateContract
import com.yc.pyq.mvp.presenter.UserUptatePresenter
import com.yc.pyq.utils.PictureSelectorTool
import com.yc.pyq.weight.FullyGridLayoutManager
import kotlinx.android.synthetic.main.f_name_head.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.ArrayList

/**ImageAdapter
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/24
 * Time: 19:37
 */
class NameHeadFrg : BaseFragment(), UserUpdateContract.View, View.OnClickListener{


    fun newInstance(): NameHeadFrg {
        val args = Bundle()
        val fragment = NameHeadFrg()
        fragment.arguments = args
        return fragment
    }

    override fun onSucees() {
        UIHelper.startMainAct()
        act?.finish()
    }

     val mPresenter by lazy { UserUptatePresenter() }

     var adapter: ImageAdapter? = null

     val localMediaList = ArrayList<LocalMedia>()

    override fun getLayoutId(): Int = R.layout.f_name_head

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.set_name_head))
        mPresenter.init(this, act)
        bt_sure.setOnClickListener(this)
        adapter = ImageAdapter(act, ImageAdapter.onAddPicClickListener {
            adapter?.setSelectMax(1)
            act?.let { PictureSelectorTool.PictureSelectorImage(it, localMediaList as List<LocalMedia>, 1, PictureConfig.CHOOSE_REQUEST) }
        })
        recyclerView.setLayoutManager(FullyGridLayoutManager(act, 3, GridLayoutManager.VERTICAL, false))
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(adapter)
        adapter?.setOnItemClickListener({ position, v ->
            val media = localMediaList[position]
            act?.let { PictureSelectorTool.PictureMediaType(it, localMediaList, position) }
        })
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onMainCameraInEvent(event: CameraInEvent) {
        localMediaList.clear()
        localMediaList.addAll(PictureSelector.obtainMultipleResult(event.`object` as Intent))
        adapter?.setList(localMediaList)
        adapter?.notifyDataSetChanged()
        val path = localMediaList[0].compressPath
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.bt_sure -> {mPresenter.onUpdate(localMediaList, et_name.text.toString())}
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            localMediaList.addAll(PictureSelector.obtainMultipleResult(data))
//            adapter?.setList(localMediaList)
//            adapter?.notifyDataSetChanged()
//        }
//    }

}