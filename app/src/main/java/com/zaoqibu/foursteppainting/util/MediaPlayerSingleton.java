package com.zaoqibu.foursteppainting.util;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerSingleton
{
	private volatile static MediaPlayerSingleton uniqueInstance;
	
	private MediaPlayer player = null;
	
	private MediaPlayerSingleton()
	{
	}
	
	public void play(Context context, int soundResId)
	{
		if (soundResId == 0)
			return;
		
		synchronized (MediaPlayer.class) {
			release();
	    	player = MediaPlayer.create(context, soundResId);
		}
    	
		player.start();
	}
	
	public void release()
	{
    	if (player!=null)
    	{
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
