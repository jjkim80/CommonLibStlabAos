package com.dkitec.commonlib_stalb_aos.webview;

import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 커스텀 WebChromeClient 클래스
 *
 * @author 신상배, 신지원
 * @version 1.0 웹페이지에서 일어나는 액션들에 관한 콜백함수
 * @date 2022-02-21
 */
public class MyWebChromeClient extends WebChromeClient {
    //현재 로딩되는 페이지 상태 표시
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    //웹에서 새 창을 열 때 호출
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    //웹뷰가 창 닫을 때 호출
    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
    }
}
