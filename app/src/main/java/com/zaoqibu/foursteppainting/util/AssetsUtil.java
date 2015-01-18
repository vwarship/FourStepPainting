package com.zaoqibu.foursteppainting.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vwarship on 2015/1/16.
 */
public class AssetsUtil {
    public static InputStream readAssetsFile(Context context, String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

}
