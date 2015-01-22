package com.zaoqibu.foursteppainting.util;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by vwarship on 2015/1/8.
 */
public class VibratorUtil extends Activity {
    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }
}
