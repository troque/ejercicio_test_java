package com.bitdubai.sub_app.wallet_store.util;

import android.util.Log;

import com.wallet_store.bitdubai.BuildConfig;


/**
 * Common Logger Utility
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class CommonLogger {

    /**
     * Send Log type info only if the signature is debug type
     *
     * @param tag String Tag name
     * @param msg message to send
     */
    public static void info(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    /**
     * Send Log type debug only if the signature is debug type
     *
     * @param tag String Tag name
     * @param msg message to send
     */
    public static void debug(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * Send Log type error only if the signature is debug type
     *
     * @param tag String Tag name
     * @param msg message to send
     */
    public static void error(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * Send Log type error only if the signature is debug type
     *
     * @param tag String Tag name
     * @param msg message to send
     * @param ex  Throwable exception to send stackTrace
     */
    public static void exception(String tag, String msg, Throwable ex) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, ex);
        }
    }


}
