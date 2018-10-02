package io.sharkbet.sharkbet.utils;

import android.support.annotation.NonNull;
import android.util.Log;

public class StringUtils {

    public static int stringToInt(@NonNull String str) {
        if(str.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            Log.e("StringUtils", "stringToInt: ", e);
            return -99;
        }
    }

}
