package com.yc.pyq.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yc.pyq.R
import com.yc.pyq.base.BaseFragment
import com.yc.pyq.mvp.impl.AboutContract
import com.yc.pyq.mvp.presenter.AboutPresenter
import com.yc.pyq.mvp.presenter.BingPhonePresenter
import kotlinx.android.synthetic.main.a_html.*

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/13
 * Time: 18:45
 *  关于我们
 */
class AboutFrg : BaseFragment(), AboutContract.View{

    val mPresenter by lazy { AboutPresenter() }


    override fun getLayoutId(): Int =  R.layout.f_about

    override fun initParms(bundle: Bundle) {
    }

    override fun initView(rootView: View) {
        setTitle(getString(R.string.about_us))
        mPresenter.init(this, act)
        mPresenter.onIntroduction()
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                LogUtils.e(url)
                return true
            }

            override fun onReceivedError(var1: WebView, var2: Int, var3: String, var4: String) {
                progressBar.setVisibility(View.GONE)
                ToastUtils.showShort("网页加载失败")
            }
        })
        //进度条
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE)
                    return
                }
                progressBar.setVisibility(View.VISIBLE)
                progressBar.setProgress(newProgress)
            }
        })
    }

    override fun setUrl(content: String?) {
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
    }


}