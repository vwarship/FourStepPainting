package com.zaoqibu.foursteppainting.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class History 
{
	private SharedPreferences pref;
	
	public History(Context context)
	{
		pref = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public void putPainting(String paintingName)
	{
		pushHistory();
		putPainting(0, paintingName);
	}
	
	private void pushHistory()
	{
		final int maxHistoryNum = 10;
		int n = Math.min(count(), maxHistoryNum-1);
		
		for (int curIndex=n-1; curIndex>=0; --curIndex)
		{
			String curValue = getPainting(curIndex);
			
			putPainting(curIndex+1, curValue);
		}
	}
	
	private void putPainting(int index, String paintingName)
	{
		String key = String.format("%d", index);
		
		Editor editor = pref.edit();
		editor.putString(key, paintingName);
		editor.commit();
	}

	public String getPainting(int index)
	{
		return pref.getString(String.format("%d", index), "");
	}
	
	public int count()
	{
		return pref.getAll().size();
	}
	
}
