package com.zaoqibu.foursteppainting.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class MediaPlayerSingleton
{
    private final static String TAG = "MediaPlayerSingleton";
	private volatile static MediaPlayerSingleton uniqueInstance;
	
	private MediaPlayer player = null;
	
	private MediaPlayerSingleton() {
        player = new MediaPlayer();
	}

    private static MediaPlayer create(Context context, String assetsPath) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(assetsPath);
            if (afd == null) return null;

            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
            return mp;
        } catch (IOException ex) {
            Log.d(TAG, "create failed:", ex);
            // fall through
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "create failed:", ex);
            // fall through
        } catch (SecurityException ex) {
            Log.d(TAG, "create failed:", ex);
            // fall through
        }

        return null;
    }

    public void play(Context context, String soundAssetsPath)
	{
        synchronized (MediaPlayer.class) {
            release();
            player = create(context, soundAssetsPath);
        }

        player.start();
	}
	
	public void release() {
    	if (player!=null) {
    		player.release();
    	}
	}
	
	public static MediaPlayerSingleton getInstance()
	{
		if (uniqueInstance == null)
		{
			synchronized (MediaPlayerSingleton.class) {
				if (uniqueInstance == null)
					uniqueInstance = new MediaPlayerSingleton();
			}
		}
		
		return uniqueInstance;
	}

}
