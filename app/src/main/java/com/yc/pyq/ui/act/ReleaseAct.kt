package com.yc.pyq.ui.act

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.entity.LocalMedia
import com.yc.pyq.R
import com.yc.pyq.adapter.ImageAdapter
import com.yc.pyq.base.BaseActivity
import com.yc.pyq.bean.DataBean
import com.yc.pyq.mvp.impl.ReleaseContract
import com.yc.pyq.mvp.presenter.ReleasePresenter
import com.yc.pyq.ui.bottom.CameraBottomFrg
import com.yc.pyq.utils.PictureSelectorTool
import com.yc.pyq.weight.FullyGridLayoutManager
import kotlinx.android.synthetic.main.f_release.*
import java.util.ArrayList

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/30
 * Time: 11:47
 * 发布
 */
class ReleaseAct : BaseActivity(), ReleaseContract.View, View.OnClickListener{

    private val cameraBottomFrg by lazy { CameraBottomFrg() }

    private val localMediaList = ArrayList<LocalMedia>()
    private var imageAdapter: ImageAdapter? = null
    private val mPresenter by lazy { ReleasePresenter() }

    var url:String? = null

    var type:Int = 4

    override fun getLayoutId(): Int = R.layout.f_release

    override fun initView() {
        setTitle1("", getString(R.string.send))
        bt_sure.setOnClickListener(this)
        fy_close.setOnClickListener(this)
        mPresenter?.init(this, act)
        imageAdapter = ImageAdapter(act) {
            when(type){
                2 ->{
                    act?.let { PictureSelectorTool.PictureSelectorImage(it, localMediaList as List<LocalMedia>, 9, PictureConfig.CHOOSE_REQUEST, false, false) }
                }
                3 -> {
                    PictureSelectorTool.SelectorVideo(act, PictureConfig.CHOOSE_REQUEST)
                }
            }
//            PictureSelectorTool.PictureSelectorVideo(act)
        }
        recyclerView.setLayoutManager(FullyGridLayoutManager(act, 3, GridLayoutManager.VERTICAL, false))
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(imageAdapter)
        imageAdapter?.setOnItemClickListener({ position, v ->
            val media = localMediaList[position]
            PictureSelectorTool.PictureMediaType(act, localMediaList, position)
        })
        cameraBottomFrg.setCameraListener(object : CameraBottomFrg.onCameraListener{
            override fun camera() {
                type = 2
                localMediaList.clear()
                recyclerView.visibility = View.VISIBLE
                fy_url.visibility = View.GONE
                imageAdapter?.setSelectMax(9)
                act?.let { PictureSelectorTool.PictureSelectorImage(it, localMediaList as List<LocalMedia>, 9, PictureConfig.CHOOSE_REQUEST, false, false) }
            }

            override fun photo() {
                type = 3
                localMediaList.clear()
                recyclerView.visibility = View.VISIBLE
                fy_url.visibility = View.GONE
                imageAdapter?.setSelectMax(1)
                imageAdapter?.isVideoImg = true
                PictureSelectorTool.SelectorVideo(act, PictureConfig.CHOOSE_REQUEST)
            }

            override fun save() {
            }

            override fun url(url1: String?) {
                url = url1
                mPresenter.onCircleDownload(url)
            }

        })
    }

    override fun initParms(bundle: Bundle) {
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.bt_sure -> {
                cameraBottomFrg.show(supportFragmentManager, "dialog")
            }
            R.id.fy_close -> {
                fy_url.visibility = View.GONE
            }
        }
    }

    override fun setOnRightClickListener() {
        super.setOnRightClickListener()
        mPresenter.onRelease(type, et_text.text.toString(), localMediaList, url)
    }

    override fun setDow(bean: DataBean?) {
        type = 1
        recyclerView.visibility = View.GONE
        fy_url.visibility = View.VISIBLE
        tv_title.text = bean?.title
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    localMediaList.addAll(PictureSelector.obtainMultipleResult(data))
                    imageAdapter?.setList(localMediaList)
                    imageAdapter?.notifyDataSetChanged()
                }
            }
        }
    }

}
