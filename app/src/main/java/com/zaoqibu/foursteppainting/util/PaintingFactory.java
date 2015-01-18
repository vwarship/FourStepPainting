package com.zaoqibu.foursteppainting.util;

import java.lang.reflect.Field;

import com.zaoqibu.foursteppainting.domain.Painting;
import com.zaoqibu.foursteppainting.domain.Picture;
import com.zaoqibu.foursteppainting.R;

public class PaintingFactory 
{
    //TODO
//	public static Painting create(String prefix)
//	{
//		Painting painting = new Painting(getStringIdWithResource(prefix),
//				getRawIdWithResource(prefix), prefix);
//
//		for (int i=1; i<=4; ++i)
//		{
//			String name = String.format("%s%d", prefix, i);
//
//			int drawableId = getDrawableIdWithResource(name);
//			int contentId = getStringIdWithResource(name);
//			int soundId = getRawIdWithResource(name);
//
//			painting.add(new Picture(i, drawableId, contentId, soundId));
//		}
//
//		return painting;
//	}
	
	private static int getDrawableIdWithResource(String name)
	{
		try {
			Field field = R.drawable.class.getField(name);
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private static int getStringIdWithResource(String name)
	{
		try {
			Field field = R.string.class.getField(name);
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
//	private static int getRawIdWithResource(String name)
//	{
//		try {
//			Field field = R.raw.class.getField(name);
//			return Integer.parseInt(field.get(null).toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return 0;
//	}

}
