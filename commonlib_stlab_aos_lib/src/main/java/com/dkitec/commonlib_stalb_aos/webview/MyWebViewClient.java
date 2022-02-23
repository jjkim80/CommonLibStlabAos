package com.dkitec.commonlib_stalb_aos.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 커스텀 WebViewClient 클래스
 *
 * @author 신상배, 신지원
 * @version 1.0 웹페이지를 로딩할 때 생이근ㄴ 콜백함수
 * @date 2022-02-21
 */
public class MyWebViewClient extends WebViewClient {
    //로딩 시작될 때
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    //새로운 URL이 webview에 로드되려 할 경우 컨트롤을 대신할 기회를 줌
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("tel:")) {
            Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            view.getContext().startActivity(tel);
        } else if (url.startsWith("mailto:")) {
            Intent mailto = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
            view.getContext().startActivity(mailto);
        } else {
            view.loadUrl(url);
        }
        return true;
    }

    //로딩 완료됐을 때 한번만 호출
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    //리소스를 로드하는 중 여러번 호출
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    //방문 내역을 히스토리에 업데이트 할 때
    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    //http 인증 요청이 있는 경우, 기본 동작은 요청 취소
    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }
}
