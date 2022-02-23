package com.dkitec.commonlib_stalb_aos.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.dkitec.commonlib_stalb_aos.R;


/**
 * 로그에 관련된 클래스
 *
 * @author 신상배
 * @version 1.0 메시지 또는 에러 메시지 출력하기 위한 클래스
 * @date 2022-01-14
 */
public class Popup {

    private static final Integer IMG_SIZE = 118;

    public Popup() {
    }


    /**
     * screen/display 사이즈를 가져온다.
     *
     * @param context Context 클래스
     * @return 디스플레이 사이즈 반환
     */
    @SuppressWarnings("deprecation")
    public static Point getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return new Point(width, height);
    }

    /**
     * 화면이 가로모드인지 세로모드인이지 확인
     *
     * @param context Context 클래스
     * @return true : 세로모드 false: 가로모드
     */
    public static boolean isOrientationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 에러 메시지 팝업을 생성한다.
     *
     * @param context Context 클래스
     * @param errorString 에러 메시지
     */
    public static void showErrorDialog(Context context, String errorString) {
        new AlertDialog.Builder(context).setTitle(R.string.error)
                .setMessage(errorString)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    /**
     * 메시지 팝업을 생성한다.
     *
     * @param context Context 클래스
     * @param resourceId 리로스 아이디
     */
    public static void showOopsDialog(Context context, int resourceId) {
        new AlertDialog.Builder(context).setTitle(R.string.oops)
                .setMessage(context.getString(resourceId))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setIcon(R.drawable.ic_action_alerts_and_states_warning)
                .create()
                .show();
    }
}
