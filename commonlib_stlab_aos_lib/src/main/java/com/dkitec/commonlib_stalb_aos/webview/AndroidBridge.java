package com.dkitec.commonlib_stalb_aos.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


/**
 * 웹뷰 인터페이스 클래스
 *
 * @author 신상배, 신지원
 * @version 1.0 자바스크립트 코드를 안드로이드에 결합
 * @date 2022-02-21
 */
public class AndroidBridge {
    Context mContext;

    AndroidBridge(Context c) {
        mContext = c;
    }

    //버튼 클릭 시 토스트메시지 띄움
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
