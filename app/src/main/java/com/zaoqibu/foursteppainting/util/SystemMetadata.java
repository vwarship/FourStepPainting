package com.zaoqibu.foursteppainting.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class SystemMetadata
{
	static public int getScreenWidth(Activity activity)
	{
    	DisplayMetrics metrics = new DisplayMetrics();
    	activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        return metrics.widthPixels;
    }

}
