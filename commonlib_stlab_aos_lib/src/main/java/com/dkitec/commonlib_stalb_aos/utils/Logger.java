package com.dkitec.commonlib_stalb_aos.utils;

import android.util.Log;

import com.dkitec.commonlib_stalb_aos.BaseApplication;

/**
 * 로그에 관련된 클래스
 *
 * @author 신상배
 * @version 1.0 Log를 출력하기 위한 클래스
 * @date 2022-01-14
 */
public class Logger {

    private static final boolean isLogPrint = BaseApplication.DEBUG ? true : false;
    private static final String TAG = "dkitec";
    private static final int MAX_INDEX = 2000;
    private static final int LOG_DEBUG = 0;
    private static final int LOG_VERBOSE = 1;
    private static final int LOG_INFO = 2;
    private static final int LOG_WARN = 3;
    private static final int LOG_ERROR = 4;

    public static synchronized void d() {
        if (isLogPrint) dLong("", LOG_DEBUG);
    }

    public static synchronized void d(String msg) {
        if (isLogPrint) dLong(msg, LOG_DEBUG);
    }

    public static synchronized void v(String msg) {
        if (isLogPrint) dLong(msg, LOG_VERBOSE);
    }

    public static synchronized void i(String msg) {
        if (isLogPrint) dLong(msg, LOG_INFO);
    }

    public static synchronized void w(String msg) {
        if (isLogPrint) dLong(msg, LOG_WARN);
    }

    public static synchronized void e(String msg) {
        if (isLogPrint) dLong(msg, LOG_ERROR);
    }

    public static void e(String msg, Exception e) {
        if (isLogPrint) Log.e(TAG, msg, e);
    }

    /**
     * Json 문자열을 보기 좋게 출력하기 위한 메서드
     *
     * @param jsonString 로그에 찍을 json 문자열
     */
    public static String getPretty(String jsonString) {
        final String INDENT = "    ";
        StringBuffer prettyJsonSb = new StringBuffer();

        int indentDepth = 0;
        String targetString = null;
        for (int i = 0; i < jsonString.length(); i++) {
            targetString = jsonString.substring(i, i + 1);
            if (targetString.equals("{") || targetString.equals("[")) {
                prettyJsonSb.append(targetString).append("\n");
                indentDepth++;
                for (int j = 0; j < indentDepth; j++) {
                    prettyJsonSb.append(INDENT);
                }
            } else if (targetString.equals("}") || targetString.equals("]")) {
                prettyJsonSb.append("\n");
                indentDepth--;
                for (int j = 0; j < indentDepth; j++) {
                    prettyJsonSb.append(INDENT);
                }
                prettyJsonSb.append(targetString);
            } else if (targetString.equals(",")) {
                prettyJsonSb.append(targetString);
                prettyJsonSb.append("\n");
                for (int j = 0; j < indentDepth; j++) {
                    prettyJsonSb.append(INDENT);
                }
            } else {
                prettyJsonSb.append(targetString);
            }
        }
        return prettyJsonSb.toString();
    }

    /**
     * 메서드명 포함한 로그 문자열로 리턴
     *
     * @param log 로그 문자열
     */
    private static String getWithMethodName(String log) {
        //로그 찍을때 메서드명도 찍기
        try {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[5];
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            sb.append(ste.getFileName().replace(".java", ""));
            sb.append("::");
            sb.append(ste.getMethodName());
            sb.append("]   ");
            sb.append(log);
            return sb.toString();
        } catch (Throwable e) {
            e.printStackTrace();
            return log;
        }
    }

    /**
     * 출력 필텅링에 의한 로그를 출력하며 MAX_INDEX값을 지정하면 값이 넘어갔을경우
     * 재귀호출하여 로그를 출력
     *
     * @param theMsg 메시지
     * @param logType 로그타입
     */
    private static synchronized void dLong(String theMsg, int logType) {
        try {
            // String to be logged is longer than the max...
            if (theMsg.length() > MAX_INDEX) {
                String theSubstring = theMsg.substring(0, MAX_INDEX);
                int theIndex = MAX_INDEX;

                //로그 찍을때 메서드명도 찍기
                theSubstring = getWithMethodName(theSubstring);

                // Log the substring.
                switch (logType) {
                    case LOG_DEBUG:
                        Log.d(TAG, theSubstring);
                        break;
                    case LOG_VERBOSE:
                        Log.v(TAG, theSubstring);
                        break;
                    case LOG_INFO:
                        Log.i(TAG, theSubstring);
                        break;
                    case LOG_WARN:
                        Log.w(TAG, theSubstring);
                        break;
                    case LOG_ERROR:
                        Log.e(TAG, theSubstring);
                        break;
                }

                // Recursively log the remainder.
                dLong(theMsg.substring(theIndex), logType);
            }
            // String to be logged is shorter than the max...
            else {
                //로그 찍을때 메서드명도 찍기
                theMsg = getWithMethodName(theMsg);

                // Log the substring.
                switch (logType) {
                    case LOG_DEBUG:
                        Log.d(TAG, theMsg);
                        break;
                    case LOG_VERBOSE:
                        Log.v(TAG, theMsg);
                        break;
                    case LOG_INFO:
                        Log.i(TAG, theMsg);
                        break;
                    case LOG_WARN:
                        Log.w(TAG, theMsg);
                        break;
                    case LOG_ERROR:
                        Log.e(TAG, theMsg);
                        break;
                }
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static final void printStackTrace(Throwable e) {
        if (BaseApplication.DEBUG) e.printStackTrace();
    }

}
