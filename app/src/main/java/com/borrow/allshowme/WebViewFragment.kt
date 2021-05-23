package com.borrow.allshowme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_web_view.*
import kotlinx.android.synthetic.main.fragment_web_view.view.*

class WebViewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_web_view, container, false)

        //webView 세팅
        root.webView.webViewClient = WebViewClient()
        var mWebViewSetting = root.webView.settings
        mWebViewSetting.javaScriptEnabled =true
        mWebViewSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebViewSetting.setSupportMultipleWindows(false); // 윈도우 여러개 사용여부
        mWebViewSetting.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        mWebViewSetting.useWideViewPort = true; // wide viewport 사용 여부
        mWebViewSetting.setSupportZoom(true); // Zoom사용여부
        mWebViewSetting.javaScriptCanOpenWindowsAutomatically = false; // 자바스크립트가 window.open()사용할수있는지 여부
        mWebViewSetting.loadWithOverviewMode = true; // 메타태그 허용 여부
        mWebViewSetting.builtInZoomControls = false; // 화면 확대 축소 허용 여부
        mWebViewSetting.domStorageEnabled = true; // 로컬저장소 허용 여부
        root.webView.loadUrl("https://www.naver.com/"); // 웹뷰 사이트 주소 및 시작
        return root


    }

}