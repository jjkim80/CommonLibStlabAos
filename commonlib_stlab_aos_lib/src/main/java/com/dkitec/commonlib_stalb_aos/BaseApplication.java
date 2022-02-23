package com.dkitec.commonlib_stalb_aos;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.dkitec.commonlib_stalb_aos.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseApplication extends Application {

    public static boolean DEBUG = false;

    @Override
    public void onCreate() {
        super.onCreate();
        this.DEBUG = isDebuggable(this);
        saveLog();
    }

    /**
     * 현재 디버그모드여부를 리턴
     * 디버그 모드로 빌드하였을 경우 true, 릴리즈 모드로 빌드하였을 경우 false 반환
     *
     * @param context
     * @return boolean
     */
    private static boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
        }
        return debuggable;
    }

    /**
     * 디바이스 내장메모리에 logcat 저장
     *
     */
    private void saveLog(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String time = format.format(date);

        if ( isExternalStorageWritable() ) {
            //read, write 둘다 가능

//            File appDirectory = new File( Environment.getExternalStorageDirectory()+"/CommonLib" );
            File appDirectory = new File( getExternalFilesDir(null).getAbsolutePath()+"/CommonLib" );

            File logDirectory = new File( appDirectory + "/logs" );
            File logFile = new File( logDirectory, "logcat_" + time + ".txt" );

            Logger.d("*** onCreate() - appDirectory :: "+appDirectory.getAbsolutePath());
            Logger.d("*** onCreate() - logDirectory :: "+logDirectory.getAbsolutePath());
            Logger.d("*** onCreate() - logFile :: "+logFile);

            //appDirectory 폴더 없을 시 생성
            if ( !appDirectory.exists() ) {
                appDirectory.mkdirs();
            }

            //logDirectory 폴더 없을 시 생성
            if ( !logDirectory.exists() ) {
                logDirectory.mkdirs();
            }

            //이전 logcat 을 지우고 파일에 새 로그을 씀
            try {
                Process process = Runtime.getRuntime().exec("logcat -c");
                process = Runtime.getRuntime().exec("logcat -f " + logFile);
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        } else if ( isExternalStorageReadable() ) {
            //read 만 가능
        } else {
            //접근 불가능
        }
    }

    /**
     * 외부저장소 read/write 가능 여부 확인
     * @return boolean
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            return true;
        }
        return false;
    }


    /**
     * 외부저장소 read 가능 여부 확인
     * @return boolean
     */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;
    }
}
