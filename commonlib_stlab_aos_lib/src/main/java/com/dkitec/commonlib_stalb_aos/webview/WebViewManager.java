package com.dkitec.commonlib_stalb_aos.webview;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;

import com.dkitec.commonlib_stalb_aos.utils.Constants;


/**
 * 웹뷰를 관리하는 클래스
 *
 * @author 신상배, 신지원
 * @version 1.0 웹뷰 관련된 설정
 * @date 2022-02-21
 */

public class WebViewManager {

    private Context mContext;
    private WebView myWebView;
    private String bridgeName;

    public WebViewManager() {
    }

    //기본 세팅
    public WebViewManager(Context context, WebView webView) {
        this.mContext = context;
        this.myWebView = webView;

        //Client 웹뷰에 연결
        myWebView.setWebViewClient(new MyWebViewClient());
        //WebChromeClient 설정 - 브라우저 이벤트 구현을 위해 필요
        myWebView.setWebChromeClient(new MyWebChromeClient());
    }

    public void setSettings() {
        //js 활성화 여부
        myWebView.getSettings().setJavaScriptEnabled(true);

        //자동으로 창을 열도록 js에 지시할건지
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        //이미지 리소스 자동으로 로드 설정
        myWebView.getSettings().setLoadsImagesAutomatically(true);

        //확대축소 기능 사용 여부
        myWebView.getSettings().setSupportZoom(true);

        //로컬스토리지 사용 여부
        myWebView.getSettings().setDomStorageEnabled(true);

        //앱 내부 캐시 사용 여부
        myWebView.getSettings().setAppCacheEnabled(true);

        //웹뷰 내에서 파일 엑세스 활성화 여부
        myWebView.getSettings().setAllowFileAccess(true);

        //컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        myWebView.getSettings().setLoadWithOverviewMode(true);
    }


    //웹페이지 호출
    public void loadUrl(String url) {
        myWebView.loadUrl(url);
    }

    //js 사용할 수 있도록 설정
    public void setJavascriptInterface(String bridgeName) {
        this.bridgeName = bridgeName;
        myWebView.addJavascriptInterface(new AndroidBridge(mContext), TextUtils.isEmpty(bridgeName) ? Constants.BRIDGE_DKI_TEC : bridgeName);
    }

    //js 활성화 여부
    public void setJavaScriptEnabled(boolean isEnable) {
        myWebView.getSettings().setJavaScriptEnabled(isEnable);
    }

    //자동으로 창을 열도록 js에 지시할건지
    public void setJavaScriptCanOpenWindowsAutomatically(boolean isEnable) {
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(isEnable);
    }

    //이미지 리소스 자동으로 로드 설정
    public void setLoadImagesAutomatically(boolean isEnable) {
        myWebView.getSettings().setLoadsImagesAutomatically(isEnable);
    }

    //확대축소 기능 사용 여부
    public void setSupportZoom(boolean isEnable) {
        myWebView.getSettings().setSupportZoom(isEnable);
    }

    //로컬스토리지 사용 여부
    public void setDomStorageEnabled(boolean isEnable) {
        myWebView.getSettings().setDomStorageEnabled(isEnable);
    }

    //앱 내부 캐시 사용 여부
    public void setAppCacheEnabled(boolean isEnable) {
        myWebView.getSettings().setAppCacheEnabled(isEnable);
    }

    //웹뷰 내에서 파일 엑세스 활성화 여부
    public void setAllowFileAccess(boolean isEnable) {
        myWebView.getSettings().setAllowFileAccess(isEnable);
    }

    //컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
    public void setLoadWithOverviewMode(boolean isEnable) {
        myWebView.getSettings().setLoadWithOverviewMode(isEnable);
    }
}
