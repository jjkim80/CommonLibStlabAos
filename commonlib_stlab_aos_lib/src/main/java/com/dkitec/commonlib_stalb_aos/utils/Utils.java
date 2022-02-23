package com.dkitec.commonlib_stalb_aos.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Utils에 관련된 클래스
 *
 * @author 신상배
 * @version 1.0 간단한 팝업 및 Utils을 담고 있는 클래스
 * @date 2022-01-11
 * 
 * @author 최재영
 * @version 1.1 이미지에 관련된 클래스
 * @date 2022-01-11
 */

public class Utils {

    private static final Integer IMG_SIZE = 118;

    private Utils() {
    }

    /**
     * 앱의 버전을 가져온다
     *
     * @param context Context 클래스
     * @return 앱버전 반환
     */
    public static String getAppVersionName(Context context) {
        String versionString = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0 /* basic info */);
            versionString = info.versionName;
        } catch (Exception e) {
            // do nothing
        }
        return versionString;
    }

    /**
     * 토스트 메세지를 표시한다.
     *
     * @param context Context 클래스, resourceId 토스트에 입력할 문자
     * @return 없음  
     */
    public static void showToast(Context context, int resourceId) {
        Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_LONG).show();
    }

    /**
     * 밀리세컨드로 부터 시간형식 지정
     *
     * @param millisec 밀리세컨드
     * @return 지정된 시간형식                
     */
    public static String formatMillis(int millisec) {
        int seconds = (int) (millisec / 1000);
        int hours = seconds / (60 * 60);
        seconds %= (60 * 60);
        int minutes = seconds / 60;
        seconds %= 60;

        String time;
        if (hours > 0) {
            time = String.format(Locale.ROOT, "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            time = String.format(Locale.ROOT, "%d:%02d", minutes, seconds);
        }
        return time;
    }

    /**
     * Uri부터 온 이미지를 리사이징 한다.
     *
     * @param data Uri클래스, context Context 클래스
     * @return 비트맵 이미지
     */
    public static Bitmap resizeBitmapImage(Uri data, Context context)
    {
        Bitmap resized = null;

        try {
            Uri imgUri = data;
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imgUri);

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(getPathFromUri(imgUri, context));
            } catch (IOException e) {
                Logger.printStackTrace(e);
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap bmRoated = rotateBitmap(bitmap, orientation);

            int height = bmRoated.getHeight();
            int width = bmRoated.getWidth();
            while (height > IMG_SIZE) {
                resized = Bitmap.createScaledBitmap(bmRoated, (width * IMG_SIZE) / height, IMG_SIZE, true);
                height = resized.getHeight();
                width = resized.getWidth();
            }
        } catch (Exception ex) {
            Logger.printStackTrace(ex);
        }

        return resized;
    }

    /**
     * 파일로부터 온 이미지를 리사이징 한다.
     *
     * @param file File클래스, context Context 클래스
     * @return 비트맵 이미지
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Bitmap resizeBitmapImage(File file, Context context)
    {
        Bitmap resized = null;

        try {
            Uri imgUri = Uri.fromFile(file);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imgUri);

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(file);
            } catch (IOException e) {
                Logger.printStackTrace(e);
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap bmRoated = rotateBitmap(bitmap, orientation);

            int height = bmRoated.getHeight();
            int width = bmRoated.getWidth();
            while (height > IMG_SIZE) {
                resized = Bitmap.createScaledBitmap(bmRoated, (width * IMG_SIZE) / height, IMG_SIZE, true);
                height = resized.getHeight();
                width = resized.getWidth();
            }
        } catch (Exception ex) {
            Logger.printStackTrace(ex);
        }

        return resized;
    }

    /**
     * 이미지를 회전시킨다..
     *
     * @param bitmap Bitmap 클래스, orientation 회전방향
     * @return 비트맵 이미지
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            Logger.printStackTrace(e);
            return null;
        }
    }

    /**
     * uri클래스로부터 경로를 가져온다.
     *
     * @param uri Uri클래스, context Context클래스
     * @return uri로부터 가져온 경로
     */
    public static String getPathFromUri(Uri uri, Context context){
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null );
        cursor.moveToNext();
        int index = cursor.getColumnIndex( "_data" );
        String path = cursor.getString(index);
        cursor.close();
        return path;
    }

    /**
     * Ssaid를 가져온다..
     *
     * @param context Context클래스
     * @return ssaid 값
     */
    public static String getSSAID(Context context) {
        String ssaid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return ssaid;
    }
}
