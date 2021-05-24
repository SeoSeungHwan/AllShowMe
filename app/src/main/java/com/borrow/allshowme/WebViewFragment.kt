package com.borrow.allshowme

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_web_view.*
import kotlinx.android.synthetic.main.fragment_web_view.view.*
import java.net.URISyntaxException

//TODO ERR_UNKNOWN_URL_SCHEME 오류 해결 
class WebViewFragment : Fragment() {
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_web_view, container, false)

        //webView 세팅
        root.webView.webViewClient = CustomWebViewClient()
        var mWebViewSetting = root.webView.settings
        mWebViewSetting?.javaScriptEnabled =true
        mWebViewSetting?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebViewSetting?.setSupportMultipleWindows(false); // 윈도우 여러개 사용여부
        mWebViewSetting?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        mWebViewSetting?.useWideViewPort = true; // wide viewport 사용 여부
        mWebViewSetting?.setSupportZoom(true); // Zoom사용여부
        mWebViewSetting?.javaScriptCanOpenWindowsAutomatically = false; // 자바스크립트가 window.open()사용할수있는지 여부
        mWebViewSetting?.loadWithOverviewMode = true; // 메타태그 허용 여부
        mWebViewSetting?.builtInZoomControls = false; // 화면 확대 축소 허용 여부
        mWebViewSetting?.domStorageEnabled = true; // 로컬저장소 허용 여부
        arguments?.getString("url")?.let { root.webView.loadUrl(it) }; // 웹뷰 사이트 주소 및 시작

        //로딩 ProgressBar구현
        root.webView.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if(newProgress==100){
                    root.progressBar.visibility = View.INVISIBLE
                }
            }
        }


        return root
    }

    inner class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean
        {
            if(URLUtil.isNetworkUrl(url))
            {
                return false
            }
            try
            {
                val shareIntent= Intent()
                shareIntent.action=Intent.ACTION_VIEW
                shareIntent.data= Uri.parse(url)
                startActivity(shareIntent)
            }
            catch(e: ActivityNotFoundException)
            {
            }
            return true
        }
        @RequiresApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean
        {
            val url=request?.url.toString()
            if(URLUtil.isNetworkUrl(url))
            {
                return false
            }
            try
            {
                val shareIntent= Intent()
                shareIntent.action=Intent.ACTION_VIEW
                shareIntent.data= Uri.parse(url)
                startActivity(shareIntent)
            }
            catch(e: ActivityNotFoundException)
            {
            }
            return true
        }
    }
}